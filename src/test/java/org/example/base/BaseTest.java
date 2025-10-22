package org.example.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.driver.DriverFactory;
import org.example.utils.ConfigReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.nio.file.Files;


public abstract class BaseTest {
    protected WebDriver driver;

    private String userDataDir;

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
    public void setUp() throws IOException {

        String isCI = System.getenv("CI");
        ChromeOptions options = new ChromeOptions();

        if ("true".equals(isCI)) {
            userDataDir = Files.createTempDirectory("chrome-profile-").toString();
            options.addArguments("--headless=new"); // Headless режим
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--user-data-dir=" + userDataDir);
        }


        driver = new ChromeDriver(options);
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
