package by.tanya.pizzashop.base;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestResultWatcher implements TestWatcher {

    private static final Logger logger = LogManager.getLogger(TestResultWatcher.class);

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        BaseTest testInstance = (BaseTest) context.getRequiredTestInstance();
        Allure.addAttachment("Failure Screenshot", "image/png",
                new java.io.ByteArrayInputStream(testInstance.takeScreenshot()), "png");
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        String testName = context.getDisplayName();
        logger.info("Test successful: {}", testName);
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        String testName = context.getDisplayName();
        String msg = reason.orElse("No reason provided");
        logger.warn("Test disabled: {}. Reason: {}", testName, msg);
    }
}
