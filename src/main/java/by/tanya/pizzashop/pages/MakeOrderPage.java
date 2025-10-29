package by.tanya.pizzashop.pages;

import by.tanya.pizzashop.utils.Shared;
import by.tanya.pizzashop.utils.TestData;
import by.tanya.pizzashop.utils.Urls;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class MakeOrderPage extends GeneralPage {

    @FindBy(css = "#order_date")
    private WebElement dateInput;

    @FindBy(css = "#billing_first_name")
    private WebElement firstNameInput;

    @FindBy(css = "#billing_last_name")
    private WebElement lastNameInput;

    @FindBy(css = "#billing_country")
    private WebElement countryDropDown;

    @FindBy(css = "#billing_address_1")
    private WebElement addressInput;

    @FindBy(css = "#billing_city")
    private WebElement cityInput;

    @FindBy(css = "#billing_state")
    private WebElement regionInput;

    @FindBy(css = "#billing_postcode")
    private WebElement postcodeInput;

    @FindBy(css = "#billing_phone")
    private WebElement phoneInput;

    @FindBy(css = "#billing_email")
    private WebElement emailInput;

    @FindBy(css = "#payment_method_cod")
    private WebElement paymentMethodInput;

    @FindBy(css = "#place_order")
    private WebElement orderButton;

    @FindBy(css = "#terms")
    private WebElement termsCheckbox;

    @FindBy(css = "#post-24 > div > div > h2")
    private WebElement orderAppliedStatus;

    public MakeOrderPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу заказа")
    public MakeOrderPage open() {
        super.open(Urls.ORDER);
        return this;
    }

    public void enterField(WebElement input, String field) {
        Shared.enterData(input, field);
    }

    @Step("Выбрать страну")
    public MakeOrderPage selectCountry() {
        waitClickable(countryDropDown);
        Select select = new Select(countryDropDown);
        select.selectByVisibleText(TestData.COUNTRY);
        wait.until(driver -> {
            WebElement selected = select.getFirstSelectedOption();
            return selected != null && selected.getText().equals(TestData.COUNTRY);
        });
        return this;
    }

    @Step("Проверка установки даты")
    public WebElement getDateInput() {
        return dateInput;
    }

    @Step("Выбрать способ оплаты")
    public MakeOrderPage setPaymentMethod() {
        safeClick(paymentMethodInput);
        return this;
    }

    @Step("Принять соглашения")
    public MakeOrderPage agreeWithTermsAndConditions() {
        safeClick(termsCheckbox);
        return this;
    }

    @Step("Сделать заказ")
    public MakeOrderPage clickOrderButton() {
        safeClick(orderButton);
        return this;
    }

    @Step("Проскроллить до формы заказа")
    public MakeOrderPage scrollToForm() {
        scrollToPageElement(firstNameInput);
        return this;
    }

    @Step("Заполнить форму заказа")
    public MakeOrderPage fillOrderForm() {
        enterField(firstNameInput, TestData.FIRST_NAME);
        enterField(lastNameInput, TestData.LAST_NAME);
        selectCountry();
        enterField(addressInput, TestData.ADDRESS);
        enterField(cityInput, TestData.CITY);
        enterField(regionInput, TestData.REGION);
        enterField(postcodeInput, TestData.POSTCODE);
        enterField(phoneInput, TestData.PHONE);
        enterField(emailInput, TestData.USER_EMAIL);
        return this;
    }

    @Step("Установить дату")
    public void setDate() {
        enterField(dateInput, TestData.DATE);
    }

    @Step("проверка оформления заказа")
    public boolean isOrderApplied() {
        return isVisible(orderAppliedStatus);
    }
}
