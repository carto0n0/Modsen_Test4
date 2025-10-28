package by.tanya.pizzashop.base;

import by.tanya.pizzashop.driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.tanya.pizzashop.utils.ConfigReader;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

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
            driver.manage().deleteAllCookies();
            driver.get("about:blank");

            if (driver instanceof JavascriptExecutor js) {
                js.executeScript("window.localStorage.clear(); window.sessionStorage.clear();");
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
}
