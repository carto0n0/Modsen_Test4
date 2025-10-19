package org.example.domain;

import org.example.utils.ConfigReader;
import org.example.utils.Shared;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends GeneralPage{

    @FindBy(css = "input.qty")
    private WebElement quantityInput;

    @FindBy(css = "button[name=\"update_cart\"]\n")
    private WebElement updateCartButton;

    @FindBy(css = "#post-20 .product-subtotal > span")
    private WebElement sumState;

    @FindBy(css = "a.checkout-button")
    private WebElement payBtn;

    @FindBy(css = "#coupon_code")
    private WebElement couponInput;

    @FindBy(css = ".cart-collaterals .cart-subtotal td span\n")
    private WebElement generalSumBlock;
    @FindBy(css = "#post-20  strong > span")
    private WebElement finalSumBlock;

    @FindBy(css = "#post-20 div > button")
    private WebElement applyCouponBtn;

    @FindBy(css = ".cart-discount.coupon-givemehalyava td a\n")
    private WebElement resetPromocodeBtn;

    public CartPage(WebDriver driver){
        super(driver);
    }

    public void open(){
        super.open(ConfigReader.get("cart.url"));
    }

    public boolean isCartNotEmpty(){
        return isVisible(quantityInput);
    }

    public WebElement resetCouponButton() {
        return resetPromocodeBtn;
    }

    public WebElement couponInputElement() {
        return couponInput;
    }

    public int getCurrentQuantity(){
        waitVisible(quantityInput);
        return Integer.parseInt(quantityInput.getAttribute("value"));
    }

    public void clickPayBtn() {
        safeClick(payBtn);
    }

    public void clickApplyCouponBtn() {
        scrollToPageElement(applyCouponBtn);
        safeClick(applyCouponBtn);
    }

    public void resetPromocode() {
        safeClick(resetPromocodeBtn);
    }

    public void enterCoupon() {
        Shared.enterData(couponInput, "coupon");
    }

    public double getCurrentSum() {
        waitVisible(sumState);
        return Shared.parsePrice(sumState.getText());
    }

    public double getGeneralPaymentSum() {
        waitVisible(generalSumBlock);
        return Shared.parsePrice(generalSumBlock.getText());
    }

    public double getTotalPaymentSum() {
        waitVisible(finalSumBlock);
        return Shared.parsePrice(finalSumBlock.getText());
    }

    public void openCartAndChoosePizza(PizzaPage pizzaPage) {
        this.open();
        if(!this.isCartNotEmpty()){
            pizzaPage.open();
            pizzaPage.addFirstPizzaToCart();
            waitForPageLoad();
            pizzaPage.clickDetailsButton();
            for (int i = 0; i < 10; i++) {
                if (pizzaPage.getCartCount() >= 1) break;
                try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
            }
            this.open();
        }
    }

    public void changeQuantity(int steps, boolean increase){
        int current = getCurrentQuantity();
        int newValue = increase ? current + steps : Math.max(1, current - steps);

        try {
            setQuantity(newValue);
        } catch (StaleElementReferenceException e) {
            PageFactory.initElements(driver, this);
            wait.until(ExpectedConditions.elementToBeClickable(quantityInput));
            setQuantity(newValue);
        }
        wait.until(ExpectedConditions.textToBePresentInElementValue(quantityInput, String.valueOf(newValue)));
    }

    private void setQuantity(int newValue) {
        quantityInput.click();
        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(newValue));
        safeClick(updateCartButton);
    }
}
