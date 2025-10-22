package org.example.mainpage;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.example.base.BaseTest;
import org.example.domain.MainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainPageTest extends BaseTest {
    private MainPage mainPage;

    @BeforeEach
    public void setUp() throws IOException {
        super.setUp();
        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @Test
    @Order(1)
    @Description("Verify that the slider moves to the right when clicking the right arrow")
    public void testSliderOnMainPageRight(){
        Allure.step("Hover over slider", mainPage::hoverOverSlider);

        Allure.step("Get current active slide", () -> {
            String firstSlide = mainPage.getActiveSlide();

            Allure.step("Click right arrow", mainPage::clickArrowRight);

            String secondSlide = mainPage.getActiveSlide();
            Allure.step("Verify that slide changed", () ->
                    assertNotEquals(firstSlide, secondSlide, "The slide did not change by clicking on the right arrow")
            );
        });
    }

    @Test
    @Order(2)
    @Description("Verify that the slider moves to the left when clicking the left arrow")
    public void testSliderOnMainPageLeft(){
        Allure.step("Hover over slider", mainPage::hoverOverSlider);

        Allure.step("Get current active slide", () -> {
            String firstSlide = mainPage.getActiveSlide();

            Allure.step("Click left arrow", mainPage::clickArrowLeft);

            String secondSlide = mainPage.getActiveSlide();
            Allure.step("Verify that slide changed", () ->
                    assertNotEquals(firstSlide, secondSlide, "The slide did not change by clicking on the left arrow")
            );
        });
    }

    @Test
    @Order(3)
    @Description("Check that the 'Add to Cart' button appears when hovering over a drink image")
    public void testIsAddToCartButton(){
        Allure.step("Scroll to drink section", mainPage::scrollToDrinkSection);
        Allure.step("Hover over drink image", mainPage::hoverOnDrinkImage);
        Allure.step("Check if 'Add to Cart' button is visible", () -> {
            boolean buttonVisible = mainPage.isAddToCartButton();
            assertTrue(buttonVisible,"the \"add to cart\" button did not appear");
        });
    }

    @Test
    @Order(4)
    @Description("Verify that clicking on the dessert image opens the dessert page")
    public void testIsDessertPageOpened(){
        Allure.step("Scroll to dessert section", mainPage::scrollToDessertSection);
        Allure.step("Click dessert image", mainPage::clickDessertImage);
        Allure.step("Verify that dessert page opened", () -> {
            boolean pageOpened = mainPage.isDessertPageOpened();
            assertTrue(pageOpened,"the dessert page didn't open when you clicked on its picture");
        });
    }

    @Test
    @Order(5)
    @Description("Check that the 'Up' scroll arrow is visible on the page")
    public void testIsScrollArrowVisible(){
        Allure.step("Scroll to check up arrow visibility", mainPage::scrollArrowVisible);
        Allure.step("Verify that the up arrow is visible", () -> {
            boolean arrowIsVisible = mainPage.isScrollArrowVisible();
            assertTrue(arrowIsVisible,"The Up arrow is not displayed");
        });
    }

    @Test
    @Order(6)
    @Description("Verify that clicking on social network links opens new tabs")
    public void testLinks(){
        Allure.step("Get current window handles", () -> {
            List<String> list = new ArrayList<>(driver.getWindowHandles());

            Allure.step("Click on all social links", () -> {
                mainPage.getLinks().forEach(link -> link.click());
            });

            Allure.step("Wait for new tabs to open", () -> {
                new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(driver -> driver.getWindowHandles().size() > list.size());
            });

            Allure.step("Verify that new tabs opened", () -> {
                List<String> newList = new ArrayList<>(driver.getWindowHandles());
                newList.removeAll(list);
                assertTrue(newList.size() >= 1, "Links to social networks do not open in a new tab");
            });
        });
    }
}