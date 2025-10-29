package by.tanya.pizzashop.pages;

import by.tanya.pizzashop.utils.Shared;
import by.tanya.pizzashop.utils.TestData;
import by.tanya.pizzashop.utils.Urls;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends GeneralPage {

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

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть корзину")
    public CartPage open() {
        super.open(Urls.CART);
        return this;
    }

    @Step("Проверить, что корзина не пуста")
    public boolean isCartNotEmpty() {
        return isVisible(quantityInput);
    }

    public WebElement resetCouponButton() {
        return resetPromocodeBtn;
    }

    @Step("Получить текущее количество товара в корзине")
    public int getCurrentQuantity() {
        waitVisible(quantityInput);
        return Integer.parseInt(quantityInput.getAttribute("value"));
    }

    @Step("Перейти к оплате")
    public CartPage clickPayBtn() {
        safeClick(payBtn);
        return this;
    }

    @Step("Нажать кнопку применения купона")
    public CartPage clickApplyCouponBtn() {
        scrollToPageElement(applyCouponBtn);
        safeClick(applyCouponBtn);
        wait.until(driver -> getTotalPaymentSum() < getGeneralPaymentSum());
        return this;
    }

    @Step("Сбросить примененный купон")
    public CartPage resetPromocode() {
        scrollToPageElement(resetPromocodeBtn);
        safeClick(resetPromocodeBtn);
        return this;
    }

    @Step("Сбросить промокод, если он уже применён")
    public CartPage resetPromocodeIfPresent(MainPage main) {
        var resetButtons = driver.findElements(By.cssSelector("#post-20 .coupon-givemehalyava a"));
        if (!resetButtons.isEmpty()) {
            main.scrollToPageElement(resetCouponButton());
            resetPromocode();
            try {
                wait.until(ExpectedConditions.invisibilityOf(resetCouponButton()));
            } catch (Exception ignored) {
            }
        }
        return this;
    }

    @Step("Ввести купон")
    public CartPage enterCoupon() {
        scrollToPageElement(couponInput);
        Shared.enterData(couponInput, TestData.COUPON);
        return this;
    }


    @Step("Изменить количество товара на {count}, увеличение: {increase}")
    public CartPage changeQuantityAndWaitForSumUpdate(int count, boolean increase) {
        double before = getCurrentSum();

        changeQuantity(count, increase);
        wait.until(driver -> {
            double after = getCurrentSum();
            return increase ? after > before : after < before;
        });

        return this;
    }


    @Step("Получить текущую сумму товара")
    public double getCurrentSum() {
        waitVisible(sumState);
        return Shared.parsePrice(sumState.getText());
    }

    @Step("Получить общую сумму к оплате")
    public double getGeneralPaymentSum() {
        waitVisible(generalSumBlock);
        return Shared.parsePrice(generalSumBlock.getText());
    }

    @Step("Получить финальную сумму к оплате")
    public double getTotalPaymentSum() {
        waitVisible(finalSumBlock);
        return Shared.parsePrice(finalSumBlock.getText());
    }

    @Step("Открыть корзину и добавить пиццу, если корзина пуста")
    public CartPage openCartAndChoosePizza(PizzaPage pizzaPage) {
        this.open();
        if (!this.isCartNotEmpty()) {
            pizzaPage.open()
                    .addFirstPizzaToCart()
                    .clickDetailsButton();
            waitForPageLoad();
            this.open();
        }
        return this;
    }

    @Step("Изменить количество товара")
    public CartPage changeQuantity(int steps, boolean increase) {
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

        return this;
    }

    private void setQuantity(int newValue) {
        quantityInput.click();
        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(newValue));
        safeClick(updateCartButton);
    }
}
