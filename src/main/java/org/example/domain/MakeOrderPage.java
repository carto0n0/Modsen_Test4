package org.example.domain;

import org.example.utils.ConfigReader;
import org.example.utils.Shared;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class MakeOrderPage extends GeneralPage{

    @FindBy(css="#order_date")
    private WebElement dateInput;

    @FindBy(css="#billing_first_name")
    private WebElement firstNameInput;

    @FindBy(css="#billing_last_name")
    private WebElement lastNameInput;

    @FindBy(css="#billing_country")
    private WebElement countryDropDown;

    @FindBy(css="#billing_address_1")
    private WebElement addressInput;

    @FindBy(css="#billing_city")
    private WebElement cityInput;

    @FindBy(css="#billing_state")
    private WebElement regionInput;

    @FindBy(css="#billing_postcode")
    private WebElement postcodeInput;

    @FindBy(css="#billing_phone")
    private WebElement phoneInput;

    @FindBy(css="#billing_email")
    private WebElement emailInput;

    @FindBy(css="#payment_method_cod")
    private WebElement paymentMethodInput;

    @FindBy(css= "#place_order")
    private WebElement orderButton;

    @FindBy(css="#terms")
    private WebElement termsCheckbox;

    @FindBy(css = "#post-24 > div > div > h2")
    private WebElement orderAppliedStatus;

    public MakeOrderPage(WebDriver driver){
        super(driver);
    }

    public void open(){
        super.open(ConfigReader.get("order.url"));
    }

    public void enterField(WebElement input, String field) {
        Shared.enterData(input, field);
    }

    public void selectCountry(String country){
        waitClickable(countryDropDown);
        Select select =new Select(countryDropDown);
        select.selectByVisibleText(country);
        wait.until(driver -> {
            WebElement selected = select.getFirstSelectedOption();
            return selected != null && selected.getText().equals(country);
        });
    }

    public WebElement getDateInput() {
        return dateInput;
    }

    public void setPaymentMethod(){
        safeClick(paymentMethodInput);
    }

    public void agreeWithTermsAndConditions(){
        safeClick(termsCheckbox);
    }

    public void clickOrderButton(){
        safeClick(orderButton);
    }

    public void scrollToForm(){
        scrollToPageElement(firstNameInput);
    }

    public void fillOrderForm() {
        enterField(firstNameInput, "firstname");
        enterField(lastNameInput, "lastname");
        selectCountry(ConfigReader.get("country"));
        enterField(addressInput, "address");
        enterField(cityInput, "city");
        enterField(regionInput, "region");
        enterField(postcodeInput, "postcode");
        enterField(phoneInput, "phone");
        enterField(emailInput, "user.email");
    }

    public void setDate(){
        enterField(dateInput, "date");
    }

    public boolean isOrderApplied(){
        return isVisible(orderAppliedStatus);
    }
}
