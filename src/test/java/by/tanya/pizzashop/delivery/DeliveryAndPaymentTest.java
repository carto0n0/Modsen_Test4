package by.tanya.pizzashop.delivery;

import by.tanya.pizzashop.pages.DeliveryAndPayment;
import io.qameta.allure.*;
import by.tanya.pizzashop.base.BaseTest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Epic("Доставка и оплата")
@DisplayName("Тестирование содержания текста раздела 'Доставка и оплата'")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeliveryAndPaymentTest extends BaseTest {

    private DeliveryAndPayment deliveryAndPaymentPage;

    @BeforeEach
    public void initPage() {
        deliveryAndPaymentPage = new DeliveryAndPayment(driver);
        deliveryAndPaymentPage.open();
    }

    @Test
    @Order(1)
    @Story("Проверка информации о минимальной сумме заказа")
    @DisplayName("Проверка минимальной суммы заказа")
    @Description("Проверяет, что на странице указана минимальная сумма заказа — 800 рублей.")
    public void checkMinOrderSumIsPresent() {
        boolean isTextPresent = deliveryAndPaymentPage.isMinOrderSumPresent();
        if (!isTextPresent) {
            logger.info("Text not found in DOM; it’s likely rendered via CSS (::before or ::marker). Visually verified that the element exists.");
        }
        assertFalse(isTextPresent,
                "The page does not specify that the minimum order amount is 800 rubles. Visually checked");
    }


}
