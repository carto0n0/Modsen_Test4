package by.tanya.pizzashop.pages;

import by.tanya.pizzashop.utils.ConfigReader;
import by.tanya.pizzashop.utils.Shared;
import by.tanya.pizzashop.utils.TestData;
import by.tanya.pizzashop.utils.Urls;
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
        super.open(Urls.ORDER);
    }

    public void enterField(WebElement input, String field) {
        Shared.enterData(input, field);
    }

    public void selectCountry(){
        waitClickable(countryDropDown);
        Select select =new Select(countryDropDown);
        select.selectByVisibleText(TestData.COUNTRY);
        wait.until(driver -> {
            WebElement selected = select.getFirstSelectedOption();
            return selected != null && selected.getText().equals(TestData.COUNTRY);
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
        enterField(firstNameInput, TestData.FIRST_NAME);
        enterField(lastNameInput, TestData.LAST_NAME);
        selectCountry();
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
