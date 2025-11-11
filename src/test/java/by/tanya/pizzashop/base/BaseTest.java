package by.tanya.pizzashop.base;

import by.tanya.pizzashop.driver.DriverFactory;
import by.tanya.pizzashop.pages.GeneralPage;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.tanya.pizzashop.utils.ConfigReader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(TestResultWatcher.class)
public abstract class BaseTest {
    protected WebDriver driver;
    protected final Logger logger = LogManager.getLogger(getClass());


    static {
        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
    }

    @BeforeAll
    void init() throws IOException {
        ConfigReader.load();
        driver = DriverFactory.createDriver();
        logger.info("Driver has been created: {}", driver);
    }

    @BeforeEach
    void clearBrowser() {
        try {
            var handles = driver.getWindowHandles();
            String mainWindow = driver.getWindowHandle();

            for (String handle : handles) {
                if (!handle.equals(mainWindow)) {
                    driver.switchTo().window(handle);
                    driver.close();
                }
            }

            driver.switchTo().window(mainWindow);
            driver.manage().deleteAllCookies();

            if (driver instanceof JavascriptExecutor js) {
                js.executeScript("window.localStorage.clear(); window.sessionStorage.clear();");
            }

            driver.navigate().to("about:blank");

            if (driver instanceof JavascriptExecutor js) {
                js.executeScript("document.open(); document.write(''); document.close();");
            }

            logger.info("Browser state cleared (cookies, localStorage, sessionStorage)");
        } catch (Exception e) {
            logger.warn("Failed to clear browser state: {}", e.getMessage());
        }
    }

    @AfterAll
    void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Driver was successfully closed");
        }
    }

    protected void waitForPageReady() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(webDriver -> ((JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState").equals("complete"));
        } catch (Exception ignored) {}
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
