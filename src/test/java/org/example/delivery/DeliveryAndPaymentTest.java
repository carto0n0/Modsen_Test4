package org.example.delivery;

import org.example.base.BaseTest;
import org.example.domain.*;
import org.example.utils.ConfigReader;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeliveryAndPaymentTest extends BaseTest {

    private MakeOrderPage makeOrderPage;

    private CartPage cartPage;

    private PizzaPage pizzaPage;

    private AutorizationPage autorizationPage;

    private DeliveryAndPayment deliveryAndPaymentPage;

    @BeforeEach
    public void setUp(){
        super.setUp();
        makeOrderPage =new MakeOrderPage(driver);
        cartPage = new CartPage(driver);
        pizzaPage = new PizzaPage(driver);
        deliveryAndPaymentPage = new DeliveryAndPayment(driver);
        autorizationPage = new AutorizationPage(driver);
        autorizationPage.open(ConfigReader.get("account.url"));
    }

    @Test
    @Order(1)
    public void testMinimalPaymentSum() {
        autorizationPage.authorize();

        cartPage.openCartAndChoosePizza(pizzaPage);
        assertTrue(cartPage.isCartNotEmpty(),"CartPage is empty");
        cartPage.clickPayBtn();

        makeOrderPage.scrollToForm();

        makeOrderPage.fillOrderForm();
        makeOrderPage.setPaymentMethod();
        makeOrderPage.agreeWithTermsAndConditions();
        makeOrderPage.clickOrderButton();

        double price = deliveryAndPaymentPage.getCurrentPrice();

        assertTrue(price >= 800.0, "Sum less than 800");
    }

}
