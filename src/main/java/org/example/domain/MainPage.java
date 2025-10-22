package org.example.domain;

import org.example.utils.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MainPage extends GeneralPage{

    @FindBy(css = "#accesspress_store_product-5 > ul")
    private WebElement slider;

    @FindBy(css = "#accesspress_store_product-7  li:nth-child(1)  a:nth-child(1) ")
    private WebElement drinkImage;

    @FindBy(css = "#accesspress_store_product-7 a.add_to_cart_button")
    private WebElement addToCartButton;

    @FindBy(css = "#accesspress_store_product-6  a:nth-child(1) ")
    private WebElement dessertImage;

    @FindBy(css = "#product-437 > div.summary.entry-summary > h1")
    private WebElement dessertTitle;

    @FindBy(css = "#ak-top")
    private WebElement scrollArrow;

    @FindBy(css = "#accesspress_store_product-7")
    private WebElement drinkSection;

    @FindBy(css = "#accesspress_store_product-6")
    private WebElement dessertSection;

    @FindBy(css = "#top-footer > :nth-child(1)")
    private WebElement footer;

    @FindBy(css = "#accesspress_cta_simple-2 :nth-child(n+3):nth-child(-n+5) a")
    private List<WebElement> links;

    @FindBy(xpath = "//*[@id=\"accesspress_store_product-5\"]/ul/a[2]")
    private WebElement rightArrow;

    @FindBy(css = "#accesspress_store_product-5 .slick-prev")
    private WebElement leftArrow;

    public MainPage(WebDriver driver){
        super(driver);
    }

    public void open(){
        super.open(ConfigReader.get("main.url"));
    }

    public void scrollToDrinkSection() {
        scrollToPageElement(drinkSection);
    }

    public void hoverOnDrinkImage(){
        hover(drinkImage);
    }

    public boolean isAddToCartButton(){
        return isVisible(addToCartButton);
    }

    public void scrollToDessertSection() {
        scrollToPageElement(dessertSection);
    }

    public void clickDessertImage(){
       safeClick(dessertImage);
    }

    public boolean isDessertPageOpened(){
        return isVisible(dessertTitle);
    }

    public void scrollArrowVisible(){
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(
                        "window.scrollTo(0, document.body.scrollHeight);"
                );
    }

    public boolean isScrollArrowVisible(){
        return  isVisible(scrollArrow);
    }

    public void hoverOverSlider(){
        waitVisible(slider);
        hover(slider);
    }

    public void clickArrowRight(){
        clickElement(rightArrow);
    }

    public void clickArrowLeft(){
        clickElement(leftArrow);
    }

    public void scrollToFooter(){
        scrollToPageElement(footer);
    }

    public List<WebElement> getLinks(){
        scrollToFooter();
        return waitVisibleList(links);
    }

    public String getActiveSlide() {
        WebElement activeSlide = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#accesspress_store_product-5 li:nth-child(5)")
        ));
        return activeSlide.getAttribute("aria-hidden");
    }
}
