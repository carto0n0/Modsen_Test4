package by.tanya.pizzashop.pages;

import by.tanya.pizzashop.utils.ConfigReader;
import by.tanya.pizzashop.utils.Shared;
import by.tanya.pizzashop.utils.Urls;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class BonusProgramPage extends GeneralPage{

    @FindBy(css = "#bonus_username")
    private WebElement nameInput;

    @FindBy(css = "#bonus_phone")
    private WebElement phoneInput;

    @FindBy(css = "#bonus_main > button")
    private WebElement bonusButton;

    public BonusProgramPage(WebDriver driver){
        super(driver);
    }

    public void open(){
        super.open(Urls.BONUS);
    }


    public void enterField(WebElement input, String data){
        Shared.enterData(input, data);
    }

    public void FillBonusForm(){
        enterField(nameInput, "firstname");
        enterField(phoneInput, "phone");
        safeClick(bonusButton);
    }

    public boolean isBonusFormSent(){
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            alert.accept();
            return alertText.contains("Заявка отправлена, дождитесь, пожалуйста, оформления карты!");
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
