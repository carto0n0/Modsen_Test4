package org.example.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.driver.DriverFactory;
import org.example.utils.ConfigReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;


public abstract class BaseTest {
    protected WebDriver driver;

    protected final Logger logger = LogManager.getLogger(getClass());


    static {
        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
    }

    @BeforeAll
    static void init(){
        ConfigReader.load();
        LogManager.getLogger(BaseTest.class).info("Configuration is loaded");
    }

    @BeforeEach
    public void setUp() {
        driver = DriverFactory.createDriver();
        logger.info("Driver has been created: {}", driver);
    }

    @AfterEach
    public void postTuning() throws InterruptedException{
        Thread.sleep(1000);
        if (driver != null) {
            driver.quit();
            logger.info("Driver was successfully closed");
        }
    }
}
