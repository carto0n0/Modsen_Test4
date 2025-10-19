package org.example.makeOrder;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.example.base.BaseTest;
import org.example.domain.AutorizationPage;
import org.example.domain.CartPage;
import org.example.domain.MakeOrderPage;
import org.example.domain.PizzaPage;
import org.example.utils.ConfigReader;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MakeOrderPageTest extends BaseTest {

    private MakeOrderPage makeOrderPage;

    private CartPage cartPage;

    private PizzaPage pizzaPage;

    private AutorizationPage autorizationPage;


    @BeforeEach
    public void setUp(){
        super.setUp();
        makeOrderPage =new MakeOrderPage(driver);
        cartPage = new CartPage(driver);
        pizzaPage = new PizzaPage(driver);
        autorizationPage = new AutorizationPage(driver);
        autorizationPage.open(ConfigReader.get("account.url"));
    }

    @Test
    @Order(1)
    @Description("Test set date")
    public void testSetDate(){
        Allure.step("Authorize into website");
        autorizationPage.authorize();

        Allure.step("Open cart and choose pizza");
        cartPage.openCartAndChoosePizza(pizzaPage);
        assertTrue(cartPage.isCartNotEmpty(),"CartPage is empty");

        Allure.step("Press pay button");
        cartPage.clickPayBtn();
        makeOrderPage.setDate();

        WebElement dateInput = makeOrderPage.getDateInput();
        String value = dateInput.getAttribute("value").trim();
        assertTrue(!value.isEmpty(), "Date is empty");
        assertTrue(value.matches("\\d{4}-\\d{2}-\\d{2}"), "The date has an incorrect format.");
    }

    @Test
    @Order(2)
    public void testFillOrderForm(){
        autorizationPage.authorize();

        cartPage.openCartAndChoosePizza(pizzaPage);
        assertTrue(cartPage.isCartNotEmpty(),"CartPage is empty");
        cartPage.clickPayBtn();

        makeOrderPage.scrollToForm();

        makeOrderPage.fillOrderForm();
        makeOrderPage.setPaymentMethod();
        makeOrderPage.agreeWithTermsAndConditions();
        makeOrderPage.clickOrderButton();

        assertTrue(makeOrderPage.isOrderApplied(), "Order not applied");
    }
}
