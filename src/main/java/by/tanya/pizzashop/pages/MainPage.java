package by.tanya.pizzashop.pages;

import by.tanya.pizzashop.utils.Urls;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MainPage extends GeneralPage {

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

    @FindBy(css = "#accesspress_store_product-5 .slick-next")
    private WebElement rightArrow;

    @FindBy(css = "#accesspress_store_product-5 .slick-prev")
    private WebElement leftArrow;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть главную страницу")
    public MainPage open() {
        super.open(Urls.BASE);
        return this;
    }

    @Step("Проскроллить до секции с напитками")
    public MainPage scrollToDrinkSection() {
        scrollToPageElement(drinkSection);
        return this;
    }

    @Step("Навести курсор на карточку напитка")
    public MainPage hoverOnDrinkImage() {
        hover(drinkImage);
        return this;
    }

    @Step("Проверить наличие кнопки 'В корзину' ")
    public boolean isAddToCartButton() {
        return isVisible(addToCartButton);
    }

    @Step("Проскроллить до секции с дессертами")
    public MainPage scrollToDessertSection() {
        scrollToPageElement(dessertSection);
        return this;
    }

    @Step("Кликнуть на карточку напитка")
    public MainPage clickDessertImage() {
        safeClick(dessertImage);
        return this;
    }

    @Step("Проверить открылась ли страница дессерта")
    public boolean isDessertPageOpened() {
        return isVisible(dessertTitle);
    }

    @Step("Проскроллить вниз страницы, для появления стрелки 'Вверх'")
    public MainPage scrollArrowVisible() {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(
                        "window.scrollTo(0, document.body.scrollHeight);"
                );
        return this;
    }

    @Step("Проверить наличие стрелки 'Вверх'")
    public boolean isScrollArrowVisible() {
        return isVisible(scrollArrow);
    }

    @Step("Навести курсор на слайдер")
    public MainPage hoverOverSlider() {
        waitVisible(slider);
        hover(slider);
        return this;
    }

    @Step("Нажать на правую стрелку в слайдере")
    public MainPage clickArrowRight() {
        scrollToPageElement(slider);
        String currentIndex = getActiveSlide();
        safeClick(rightArrow);
        wait.until(driver -> {
            String newIndex = getActiveSlide();
            return !newIndex.equals(currentIndex);
        });
        return this;
    }

    @Step("Нажать на левую стрелку в слайдере")
    public MainPage clickArrowLeft() {
        scrollToPageElement(slider);
        safeClick(leftArrow);
        String currentIndex = getActiveSlide();
        safeClick(leftArrow);
        wait.until(driver -> {
            String newIndex = getActiveSlide();
            return !newIndex.equals(currentIndex);
        });
        return this;
    }

    @Step("Проскроллить до футера")
    public MainPage scrollToFooter() {
        scrollToPageElement(footer);
        return this;
    }

    @Step("Получить все ссылки на соц. сети в футере")
    public List<WebElement> getLinks() {
        scrollToFooter();
        return waitVisibleList(links);
    }

    @Step("Кликнуть по всем ссылкам на соцсети и дождаться открытия новых вкладок")
    public int clickSocialLinksAndWaitForNewTabs() {
        List<String> list = new ArrayList<>(driver.getWindowHandles());

        getLinks().forEach(link -> link.click());

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(driver -> driver.getWindowHandles().size() > list.size());

        List<String> newList = new ArrayList<>(driver.getWindowHandles());
        newList.removeAll(list);

        return newList.size();
    }

    @Step("Получить активный слайд в слайдере")
    public String getActiveSlide() {
        WebElement activeSlide = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#accesspress_store_product-5 .slick-slide.slick-active")
        ));
        return activeSlide.getAttribute("data-slick-index");
    }
}
