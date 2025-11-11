package by.tanya.pizzashop.pages;

import by.tanya.pizzashop.utils.Shared;
import by.tanya.pizzashop.utils.TestData;
import by.tanya.pizzashop.utils.Urls;
import org.openqa.selenium.*;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BonusProgramPage extends GeneralPage {

    @FindBy(css = "#bonus_username")
    private WebElement nameInput;

    @FindBy(css = "#bonus_phone")
    private WebElement phoneInput;

    @FindBy(css = "#bonus_main > button")
    private WebElement bonusButton;

    public BonusProgramPage(WebDriver driver) {
        super(driver);
    }


    @Step("Открыть страницу бонусной программы")
    public BonusProgramPage open() {
        super.open(Urls.BONUS);
        return this;
    }

    public void enterField(WebElement input, String data) {
        Shared.enterData(input, data);
    }

    @Step("Заполнить форму и отправить её")
    public BonusProgramPage fillBonusForm() {
        enterField(nameInput, TestData.FIRST_NAME);
        enterField(phoneInput, TestData.PHONE);
        safeClick(bonusButton);
        return this;
    }

    @Step("Ожидать появления алерта")
    public BonusProgramPage waitForAlert() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            logger.info("Alert appeared after the form was submitted");
        } catch (TimeoutException e) {
            logger.error("Alert didn't appear after submitting the form.", e);
        }
        return this;
    }

    @Step("Проверить, что форма была успешно отправлена")
    public boolean isBonusFormSent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            logger.info("Alert text: {}", alertText);
            alert.accept();
            return alertText.contains("Заявка отправлена");
        } catch (TimeoutException e) {
            logger.error("No alert appeared after form submission");
            return false;
        }
    }
}
