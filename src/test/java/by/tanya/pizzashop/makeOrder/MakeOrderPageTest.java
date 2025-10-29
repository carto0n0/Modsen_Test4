package by.tanya.pizzashop.makeOrder;

import by.tanya.pizzashop.base.BaseTest;
import by.tanya.pizzashop.pages.AutorizationPage;
import by.tanya.pizzashop.pages.CartPage;
import by.tanya.pizzashop.pages.MakeOrderPage;
import by.tanya.pizzashop.pages.PizzaPage;
import by.tanya.pizzashop.utils.Urls;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Страница заказа")
@DisplayName("Тестирование функционала страницы заказа")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MakeOrderPageTest extends BaseTest {

    private MakeOrderPage makeOrderPage;

    private CartPage cartPage;

    private PizzaPage pizzaPage;

    private AutorizationPage autorizationPage;


    @BeforeEach
    public void initPage() {
        makeOrderPage = new MakeOrderPage(driver);
        cartPage = new CartPage(driver);
        pizzaPage = new PizzaPage(driver);
        autorizationPage = new AutorizationPage(driver);
        autorizationPage.open(Urls.ACCOUNT);
    }

    @Test
    @Order(1)
    @Feature("Заполнение формы")
    @Story("Проверка установки даты доставки")
    @DisplayName("Установка даты доставки")
    @Description("Авторизация, добавления товара, заполнение даты заказа и проверка, что дата введена корректно.")
    public void testSetDate() {
        autorizationPage.authorize();

        cartPage.openCartAndChoosePizza(pizzaPage);
        assertTrue(cartPage.isCartNotEmpty(), "CartPage is empty");

        cartPage.clickPayBtn();
        makeOrderPage.setDate();

        WebElement dateInput = makeOrderPage.getDateInput();
        String value = dateInput.getAttribute("value").trim();

        assertTrue(!value.isEmpty(), "Date is empty");
        assertTrue(value.matches("\\d{4}-\\d{2}-\\d{2}"), "The date has an incorrect format.");
    }

    @Test
    @Order(2)
    @Feature("Заполнение формы")
    @Story("Заполнение формы заказа и подтверждение покупки")
    @DisplayName("Заполнение и подтверждение заказа")
    @Description("Авторизация, добавление товара, заполнение формы заказа, согласие с условиями и проверка успешного оформления.")
    public void testFillOrderForm() {
        autorizationPage.authorize();

        cartPage.openCartAndChoosePizza(pizzaPage);
        assertTrue(cartPage.isCartNotEmpty(), "CartPage is empty");

        cartPage.clickPayBtn();

        makeOrderPage.scrollToForm()
                .fillOrderForm()
                .setPaymentMethod()
                .agreeWithTermsAndConditions()
                .clickOrderButton();

        assertTrue(makeOrderPage.isOrderApplied(), "Order not applied");
    }
}
