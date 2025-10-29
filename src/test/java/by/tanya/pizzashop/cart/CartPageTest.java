package by.tanya.pizzashop.cart;

import io.qameta.allure.*;
import by.tanya.pizzashop.base.BaseTest;
import by.tanya.pizzashop.pages.AutorizationPage;
import by.tanya.pizzashop.pages.CartPage;
import by.tanya.pizzashop.pages.MainPage;
import by.tanya.pizzashop.pages.PizzaPage;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Корзина")
@DisplayName("Тестирование функционала корзины")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartPageTest extends BaseTest {

    private CartPage cartPage;

    private PizzaPage pizza;

    private AutorizationPage auth;

    private MainPage main;

    @BeforeEach
    public void initPage() {
        cartPage = new CartPage(driver);
        pizza = new PizzaPage(driver);
        auth = new AutorizationPage(driver);
        main = new MainPage(driver);
        cartPage.open();
    }

    @Test
    @Order(1)
    @Feature("Изменение количества товара")
    @Story("Проверка увеличения и уменьшения количества пиццы")
    @DisplayName("Изменение количества товара в корзине")
    @Description("Проверяем, что количество товара в корзине корректно увеличивается и уменьшается")
    public void testChangeProductQuantity() {

        cartPage.openCartAndChoosePizza(pizza);
        assertTrue(cartPage.isCartNotEmpty(), "CartPage is empty");

        int before = cartPage.getCurrentQuantity();

        cartPage.changeQuantity(1, true);
        int afterIncrease = cartPage.getCurrentQuantity();
        assertTrue(afterIncrease > before, "The quantity has not increased");

        cartPage.changeQuantity(1, false);
        int afterDecrease = cartPage.getCurrentQuantity();
        assertTrue(afterDecrease == before, "The quantity has not decreased");

    }

    @Test
    @Order(2)
    @Feature("Изменение количества товара")
    @Story("Обновление итоговой суммы")
    @DisplayName("Проверка изменения суммы при изменении количества товара")
    @Description("Проверяем, что изменения количества товара в корзине корректно обновляет сумму")
    public void testChangeProductSum() {

        cartPage.openCartAndChoosePizza(pizza);
        assertTrue(cartPage.isCartNotEmpty(), "CartPage is empty");

        double before = cartPage.getCurrentSum();

        cartPage.changeQuantityAndWaitForSumUpdate(1, true);
        double afterIncrease = cartPage.getCurrentSum();
        assertTrue(afterIncrease > before, "The sum has not increased");

        cartPage.changeQuantityAndWaitForSumUpdate(1, false);
        double afterDecrease = cartPage.getCurrentSum();
        assertTrue(afterIncrease > afterDecrease, "The sum has not decreased");
    }

    @Test
    @Order(3)
    @Story("Переход к оплате")
    @DisplayName("Проверка перехода к оплате после авторизации")
    @Description("Авторизуется, добавляет пиццу в корзину и проверяет переход к странице оплаты.")
    public void goToPaying() {
        auth.authorize();
        cartPage.openCartAndChoosePizza(pizza)
                .clickPayBtn();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("checkout"), "didn't go to the payment page");
    }

    @Test
    @Order(4)
    @Story("Применение промокода")
    @DisplayName("Применение промокода и проверка скидки")
    @Description("Авторизуется, добавляет пиццу в корзину, применяет промокод и проверяет, что скидка применилась.")
    public void applyPromocode() {

        auth.authorize();

        cartPage.openCartAndChoosePizza(pizza)
                .resetPromocodeIfPresent(main)
                .enterCoupon()
                .clickApplyCouponBtn();

        double generalSum = cartPage.getGeneralPaymentSum();
        double totalSum = cartPage.getTotalPaymentSum();
        assertTrue(totalSum < generalSum, "Coupon doesn't apply");
    }
}
