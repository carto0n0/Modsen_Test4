package org.example.cart;

import org.example.base.BaseTest;
import org.example.domain.AutorizationPage;
import org.example.domain.CartPage;
import org.example.domain.MainPage;
import org.example.domain.PizzaPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    public void setUp(){
        super.setUp();
        cartPage = new CartPage(driver);
        pizza = new PizzaPage(driver);
        auth = new AutorizationPage(driver);
        main = new MainPage(driver);
        cartPage.open();
    }

    @Test
    @Order(1)
    public void testChangeProductQuantity() {
        cartPage.openCartAndChoosePizza(pizza);
        assertTrue(cartPage.isCartNotEmpty(),"CartPage is empty");
        int before= cartPage.getCurrentQuantity();
        cartPage.changeQuantity(1,true);
        int afterIncrease= cartPage.getCurrentQuantity();
        assertTrue(afterIncrease>before,"The quantity has not increased");
        cartPage.changeQuantity(1,false);
        int afterDecrease = cartPage.getCurrentQuantity();
        assertTrue(afterDecrease==before,"The quantity has not decreased");
    }

    @Test
    @Order(2)
    public void testChangeProductSum() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        cartPage.openCartAndChoosePizza(pizza);
        assertTrue(cartPage.isCartNotEmpty(), "CartPage is empty");
        double before = cartPage.getCurrentSum();
        cartPage.changeQuantity(1, true);

        wait.until(driver -> cartPage.getCurrentSum() > before);

        double afterIncrease = cartPage.getCurrentSum();
        assertTrue(afterIncrease > before, "The sum has not increased");
        cartPage.changeQuantity(1, false);

        wait.until(driver -> cartPage.getCurrentSum() == before);

        double afterDecrease = cartPage.getCurrentSum();
        assertEquals(before, afterDecrease, "The sum has not decreased");
    }


    @Test
    @Order(3)
    public void goToPaying() {
        auth.authorize();
        cartPage.openCartAndChoosePizza(pizza);
        assertTrue(cartPage.isCartNotEmpty(),"CartPage is empty");
        cartPage.clickPayBtn();
    }

    @Test
    @Order(4)
    public void applyPromocode() {
        auth.authorize();
        cartPage.openCartAndChoosePizza(pizza);
        assertTrue(cartPage.isCartNotEmpty(), "CartPage is empty");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        if (!driver.findElements(By.cssSelector("#post-20 .coupon-givemehalyava a")).isEmpty()) {
            main.scrollToPageElement(cartPage.resetCouponButton());
            cartPage.resetPromocode();

            wait.until(ExpectedConditions.invisibilityOf(cartPage.resetCouponButton()));
        }

        main.scrollToPageElement(cartPage.couponInputElement());
        cartPage.enterCoupon();
        cartPage.clickApplyCouponBtn();

        double generalSum = cartPage.getGeneralPaymentSum();

        wait.until(driver -> {
            double newTotal = cartPage.getTotalPaymentSum();
            return newTotal < generalSum;
        });

        double totalSum = cartPage.getTotalPaymentSum();
        assertTrue(totalSum < generalSum, "Coupon doesn't apply");
    }

}
