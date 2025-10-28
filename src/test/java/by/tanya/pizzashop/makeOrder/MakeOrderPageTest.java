package by.tanya.pizzashop.makeOrder;

import by.tanya.pizzashop.base.BaseTest;
import by.tanya.pizzashop.pages.AutorizationPage;
import by.tanya.pizzashop.pages.CartPage;
import by.tanya.pizzashop.pages.MakeOrderPage;
import by.tanya.pizzashop.pages.PizzaPage;
import by.tanya.pizzashop.utils.ConfigReader;
import by.tanya.pizzashop.utils.Urls;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MakeOrderPageTest extends BaseTest {

    private MakeOrderPage makeOrderPage;

    private CartPage cartPage;

    private PizzaPage pizzaPage;

    private AutorizationPage autorizationPage;


    @BeforeEach
    public void initPage(){
        makeOrderPage =new MakeOrderPage(driver);
        cartPage = new CartPage(driver);
        pizzaPage = new PizzaPage(driver);
        autorizationPage = new AutorizationPage(driver);
        autorizationPage.open(Urls.ACCOUNT);
    }

    @Test
    @Order(1)
    @Description("Test set date")
    public void testSetDate(){
        Allure.step("Authorize into website", autorizationPage::authorize);

        Allure.step("Open cart and choose pizza", () -> {
            cartPage.openCartAndChoosePizza(pizzaPage);
            assertTrue(cartPage.isCartNotEmpty(),"CartPage is empty");
        });

        Allure.step("Press pay button and set date", () -> {
            cartPage.clickPayBtn();
            makeOrderPage.setDate();

            WebElement dateInput = makeOrderPage.getDateInput();
            String value = dateInput.getAttribute("value").trim();
            assertTrue(!value.isEmpty(), "Date is empty");
            assertTrue(value.matches("\\d{4}-\\d{2}-\\d{2}"), "The date has an incorrect format.");
        });
    }

    @Test
    @Order(2)
    @Description("Test fill order form and complete order")
    public void testFillOrderForm(){
        Allure.step("Authorize into website", autorizationPage::authorize);

        Allure.step("Open cart and choose pizza", () -> {
            cartPage.openCartAndChoosePizza(pizzaPage);
            assertTrue(cartPage.isCartNotEmpty(),"CartPage is empty");
        });

        Allure.step("Press pay button", cartPage::clickPayBtn);

        Allure.step("Scroll to order form", makeOrderPage::scrollToForm);

        Allure.step("Fill order form", makeOrderPage::fillOrderForm);

        Allure.step("Set payment method", makeOrderPage::setPaymentMethod);

        Allure.step("Agree with terms and conditions", makeOrderPage::agreeWithTermsAndConditions);

        Allure.step("Click order button", makeOrderPage::clickOrderButton);

        Allure.step("Verify that order is applied", () ->
                assertTrue(makeOrderPage.isOrderApplied(), "Order not applied")
        );
    }
}
