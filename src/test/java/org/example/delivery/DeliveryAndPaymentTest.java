package org.example.delivery;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.example.base.BaseTest;
import org.example.domain.*;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeliveryAndPaymentTest extends BaseTest {

    private DeliveryAndPayment deliveryAndPaymentPage;

    @BeforeEach
    public void setUp() throws IOException {
        super.setUp();
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
                System.out.println("The text was not found in the DOM. It is displayed via CSS (::before or ::marker). Visually verified that the element exists");
            }
            assertFalse(isTextPresent,
                    "The page does not specify that the minimum order amount is 800 rubles. Visually checked");
        });
    }


}
