package org.example.mainpage;

import org.example.base.BaseTest;
import org.example.domain.MainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainPageTest extends BaseTest {
    private MainPage mainPage;

    @BeforeEach
    public void setUp(){
        super.setUp();
        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @Test
    @Order(1)
    public void testSliderOnMainPageRight(){
        mainPage.hoverOverSlider();
        String firstSlide = mainPage.getActiveSlide();
        mainPage.clickArrowRight();
        String secondSlide = mainPage.getActiveSlide();
        assertNotEquals(firstSlide,secondSlide,"The slide did not change by clicking on the right arrow");
    }

    @Test
    @Order(2)
    public void testSliderOnMainPageLeft(){
        mainPage.hoverOverSlider();
        String firstSlide = mainPage.getActiveSlide();
        mainPage.clickArrowLeft();
        String secondSlide = mainPage.getActiveSlide();
        assertNotEquals(firstSlide,secondSlide,"The slide did not change by clicking on the left arrow");
    }

    @Test
    @Order(3)
    public void testIsAddToCartButton(){
        mainPage.scrollToDrinkSection();
        mainPage.hoverOnDrinkImage();
        boolean buttonVisible = mainPage.isAddToCartButton();
        assertTrue(buttonVisible,"the \"add to cart\" button did not appear");
    }

    @Test
    @Order(4)
    public void testIsDessertPageOpened(){
        mainPage.scrollToDessertSection();
        mainPage.clickDessertImage();
        boolean pageOpened =mainPage.isDessertPageOpened();
        assertTrue(pageOpened,"the dessert page didn't open when you clicked on its picture");
    }

    @Test
    @Order(5)
    public void testIsScrollArrowVisible(){
        mainPage.scrollArrowVisible();
        boolean arrowIsVisible =mainPage.isScrollArrowVisible();
        assertTrue(arrowIsVisible,"The Up arrow is not displayed");
    }

    @Test
    @Order(6)
    public void testLinks(){
        List<String> list = new ArrayList<>(driver.getWindowHandles());
        mainPage.getLinks().forEach(link -> {
            link.click();
        });
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(driver -> driver.getWindowHandles().size() > list.size());

        List<String> newList = new ArrayList<>(driver.getWindowHandles());
        newList.removeAll(list);
        assertTrue(newList.size()>=1, "Links to social networks do not open in a new tab");
    }
}
