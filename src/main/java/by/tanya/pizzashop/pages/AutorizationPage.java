package by.tanya.pizzashop.pages;

import by.tanya.pizzashop.utils.ConfigReader;
import by.tanya.pizzashop.utils.Shared;
import by.tanya.pizzashop.utils.Urls;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AutorizationPage extends GeneralPage{

    @FindBy(css="#username")
    private WebElement emailInput;

    @FindBy(css="#password")
    private WebElement passwordInput;

    @FindBy(css="#post-22 p:nth-child(3) button")
    private WebElement signInBtn;

    public AutorizationPage(WebDriver driver) {
        super(driver);
    }

    public void enterField(WebElement input, String data) {
        Shared.enterData(input, data);
    }

    public void openAuthPage() {
        super.open(Urls.ACCOUNT);
    }

    public void fillAuthForm() {
        enterField(emailInput,"user.email");
        enterField(passwordInput, "user.password");
    }

    public boolean authorize() {
        this.openAuthPage();
        this.fillAuthForm();
        this.signInBtn.click();

        return true;
    }
}
