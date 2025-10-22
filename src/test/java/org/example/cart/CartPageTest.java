package org.example.cart;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.example.base.BaseTest;
import org.example.domain.AutorizationPage;
import org.example.domain.CartPage;
import org.example.domain.MainPage;
import org.example.domain.PizzaPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartPageTest extends BaseTest{

    private CartPage cartPage;

    private PizzaPage pizza;

    private AutorizationPage auth;

    private MainPage main;

    @BeforeEach
    @Override
    public void setUp() throws IOException {
        super.setUp();
        cartPage = new CartPage(driver);
        pizza = new PizzaPage(driver);
        auth = new AutorizationPage(driver);
        main = new MainPage(driver);
        cartPage.open();
    }

    @Test
    @Order(1)
    @Description("Verify changing product quantity in the cart increases and decreases correctly")
    public void testChangeProductQuantity() {
        Allure.step("Add pizza to cart", () -> cartPage.openCartAndChoosePizza(pizza));
        Allure.step("Verify cart is not empty", () -> assertTrue(cartPage.isCartNotEmpty(), "CartPage is empty"));

        int before = cartPage.getCurrentQuantity();

        Allure.step("Increase quantity by 1", () -> {
            cartPage.changeQuantity(1,true);
            int afterIncrease = cartPage.getCurrentQuantity();
            assertTrue(afterIncrease > before, "The quantity has not increased");
        });

        Allure.step("Decrease quantity by 1", () -> {
            cartPage.changeQuantity(1,false);
            int afterDecrease = cartPage.getCurrentQuantity();
            assertTrue(afterDecrease == before, "The quantity has not decreased");
        });
    }

    @Test
    @Order(2)
    @Description("Verify that changing product quantity updates the total sum correctly")
    public void testChangeProductSum() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Allure.step("Add pizza to cart", () -> cartPage.openCartAndChoosePizza(pizza));
        Allure.step("Verify cart is not empty", () -> assertTrue(cartPage.isCartNotEmpty(), "CartPage is empty"));

        Allure.step("Increase quantity and check sum", () -> {
            double before = cartPage.getCurrentSum();
            cartPage.changeQuantity(1,true);
            wait.until(driver -> cartPage.getCurrentSum() > before);
            double afterIncrease = cartPage.getCurrentSum();
            assertTrue(afterIncrease > before, "The sum has not increased");
        });

        Allure.step("Decrease quantity and check sum", () -> {
            double before = cartPage.getCurrentSum();
            cartPage.changeQuantity(1,false);
            wait.until(driver -> cartPage.getCurrentSum() == before);
            double afterDecrease = cartPage.getCurrentSum();
            assertEquals(before, afterDecrease, "The sum has not decreased");
        });
    }

    @Test
    @Order(3)
    @Description("Go to the payment page after authorizing and selecting pizza")
    public void goToPaying() {
        Allure.step("Authorize user", auth::authorize);
        Allure.step("Add pizza to cart", () -> cartPage.openCartAndChoosePizza(pizza));
        Allure.step("Verify cart is not empty", () -> assertTrue(cartPage.isCartNotEmpty(),"CartPage is empty"));
        Allure.step("Click pay button", cartPage::clickPayBtn);
    }

    @Test
    @Order(4)
    @Description("Apply promo code and verify that the discount is applied")
    public void applyPromocode() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Allure.step("Authorize user", auth::authorize);
        Allure.step("Add pizza to cart", () -> cartPage.openCartAndChoosePizza(pizza));
        Allure.step("Verify cart is not empty", () -> assertTrue(cartPage.isCartNotEmpty(), "CartPage is empty"));

        Allure.step("Reset promo code if present", () -> {
            if (!driver.findElements(By.cssSelector("#post-20 .coupon-givemehalyava a")).isEmpty()) {
                main.scrollToPageElement(cartPage.resetCouponButton());
                cartPage.resetPromocode();
                wait.until(ExpectedConditions.invisibilityOf(cartPage.resetCouponButton()));
            }
        });

        Allure.step("Apply new promo code", () -> {
            main.scrollToPageElement(cartPage.couponInputElement());
            cartPage.enterCoupon();
            cartPage.clickApplyCouponBtn();

            double generalSum = cartPage.getGeneralPaymentSum();
            wait.until(driver -> cartPage.getTotalPaymentSum() < generalSum);
            double totalSum = cartPage.getTotalPaymentSum();
            assertTrue(totalSum < generalSum, "Coupon doesn't apply");
        });
    }
}
