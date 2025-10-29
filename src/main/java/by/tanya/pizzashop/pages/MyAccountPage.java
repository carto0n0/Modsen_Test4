package by.tanya.pizzashop.pages;

import by.tanya.pizzashop.utils.Urls;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

public class MyAccountPage extends GeneralPage {

    @FindBy(css = "#post-22 .woocommerce-MyAccount-navigation-link--edit-account> a")
    private WebElement accountInformation;

    @FindBy(css = "#uploadFile")
    private WebElement addFileButton;

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }


    @Step("Открыть страницу аккаунта")
    public MyAccountPage open() {
        super.open(Urls.ACCOUNT);
        return this;
    }

    @Step("Перейти к информации аккаунта")
    public MyAccountPage openMyAccountInfo() {
        safeClick(accountInformation);
        return this;
    }

    @Step("Добавить файл в профиль")
    public MyAccountPage addFile(String imgPath) {
        String absolutePath = Paths.get(imgPath).toAbsolutePath().toString();
        if (!Files.exists(Paths.get(absolutePath))) {
            throw new RuntimeException("File not found: " + absolutePath);
        }
        addFileButton.sendKeys(absolutePath);

        js.executeScript(
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                addFileButton
        );
        return this;
    }

    @Step("Проверить добавлен ли файл")
    public boolean isFileAdd() {
        try {
            WebElement preview = driver.findElement(By.cssSelector("#uploadPreview"));
            String hiddenAttr = preview.getAttribute("hidden");
            return hiddenAttr == null;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
