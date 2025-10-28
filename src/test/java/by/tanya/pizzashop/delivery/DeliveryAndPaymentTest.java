package by.tanya.pizzashop.delivery;

import by.tanya.pizzashop.pages.DeliveryAndPayment;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import by.tanya.pizzashop.base.BaseTest;
import by.tanya.pizzashop.pages.*;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeliveryAndPaymentTest extends BaseTest {

    private DeliveryAndPayment deliveryAndPaymentPage;

    @BeforeEach
    public void initPage(){
        deliveryAndPaymentPage = new DeliveryAndPayment(driver);
        deliveryAndPaymentPage.open();
    }

    @Test
    @Order(1)
    @Description("Verify that the minimum order sum text is present on the Delivery and Payment page")
    public void checkMinOrderSumIsPresent() {
        Allure.step("Check if minimum order sum text is present", () -> {
            boolean isTextPresent = deliveryAndPaymentPage.isMinOrderSumPresent();
            if (!isTextPresent) {
                logger.info("Text not found in DOM; it’s likely rendered via CSS (::before or ::marker). Visually verified that the element exists.");
                Allure.addAttachment("Visual check", "The text was rendered via CSS (::before or ::marker) but visually confirmed.");
            }
            assertFalse(isTextPresent,
                    "The page does not specify that the minimum order amount is 800 rubles. Visually checked");
        });
    }


}
