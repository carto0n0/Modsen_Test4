package by.tanya.pizzashop.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.List;

public abstract class GeneralPage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor js;

    protected final Logger logger = LogManager.getLogger(getClass());

    public GeneralPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    public void open(String url) {
        driver.get(url);
        driver.manage().window().maximize();
        waitForPageLoad();
    }

    protected WebElement waitVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void safeClick(WebElement element) {
        try {
            waitClickable(element).click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", element);
        }
    }

    public void hover(WebElement element) {
        actions.moveToElement(waitVisible(element)).perform();
    }

    public void scrollToPageElement(WebElement element) {
        try {
            WebElement visibleElement = waitVisible(element);
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", visibleElement);
            wait.until(driver ->
                    (Boolean) js.executeScript(
                            "const rect = arguments[0].getBoundingClientRect();" +
                                    "return rect.top >= 0 && rect.bottom <= window.innerHeight;", visibleElement)
            );
        } catch (Exception e) {
            logger.warn("Could not scroll to element: {}", e.getMessage());
        }
    }

    public boolean isVisible(WebElement element) {
        try {
            return waitVisible(element).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public List<WebElement> waitVisibleList(List<WebElement> listElement) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(listElement));
    }

    protected void waitForPageLoad() {
        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }
}
