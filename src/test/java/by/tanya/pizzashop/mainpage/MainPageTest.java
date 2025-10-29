package by.tanya.pizzashop.mainpage;

import by.tanya.pizzashop.base.BaseTest;
import by.tanya.pizzashop.pages.MainPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Главная страница")
@DisplayName("Тестирование функционала главной страницы")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainPageTest extends BaseTest {
    private MainPage mainPage;

    @BeforeEach
    public void initPage() {
        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @Test
    @Order(1)
    @Feature("Изменение карточек в слайдере")
    @Story("Работоспособность правой стрелки")
    @DisplayName("Проверка пролистывания слайдера вправо")
    @Description("Проверяет, что при клике на стрелку вправо слайд меняется.")
    public void testSliderOnMainPageRight() {

        String firstSlide = mainPage.hoverOverSlider().getActiveSlide();

        String secondSlide = mainPage.clickArrowRight().getActiveSlide();

        assertNotEquals(firstSlide, secondSlide, "The slide did not change by clicking on the right arrow");
    }

    @Test
    @Order(2)
    @Feature("Изменение карточек в слайдере")
    @Story("Работоспособность левой стрелки")
    @DisplayName("Проверка пролистывания слайдера влево")
    @Description("Проверяет, что при клике на стрелку влево слайд меняется.")
    public void testSliderOnMainPageLeft() {

        String firstSlide = mainPage.hoverOverSlider().getActiveSlide();

        String secondSlide = mainPage.clickArrowLeft().getActiveSlide();

        assertNotEquals(firstSlide, secondSlide, "The slide did not change by clicking on the left arrow");
    }

    @Test
    @Order(3)
    @Feature("Карточки товара")
    @Story("Появление кнопки 'Добавить в корзину' ")
    @DisplayName("Появление кнопки 'Добавить в корзину' при наведении на напиток")
    @Description("Проверяет, что при наведении на изображение напитка появляется кнопка добавления в корзину.")
    public void testIsAddToCartButton() {
        boolean buttonVisible = mainPage.scrollToDrinkSection()
                .hoverOnDrinkImage()
                .isAddToCartButton();

        assertTrue(buttonVisible, "the \"add to cart\" button did not appear");
    }

    @Test
    @Order(4)
    @Feature("Карточки товара")
    @Story("При клике на карточку товара открывается его страница")
    @DisplayName("Переход на страницу десерта при клике по изображению")
    @Description("Проверяет, что при клике на изображение десерта открывается соответствующая страница.")
    public void testIsDessertPageOpened() {
        boolean pageOpened = mainPage.scrollToDessertSection()
                .clickDessertImage()
                .isDessertPageOpened();

        assertTrue(pageOpened, "the dessert page didn't open when you clicked on its picture");
    }

    @Test
    @Order(5)
    @Story("Пояление стрелки 'Вверх' при скролле вниз страницы")
    @DisplayName("Отображение стрелки 'Вверх' при прокрутке страницы")
    @Description("Проверяет, что стрелка 'Вверх' появляется при прокрутке страницы вниз.")
    public void testIsScrollArrowVisible() {
        boolean arrowIsVisible = mainPage.scrollArrowVisible()
                .isScrollArrowVisible();
        assertTrue(arrowIsVisible, "The Up arrow is not displayed");
    }

    @Test
    @Order(6)
    @Story("Сыылки на соц. сети открываются в новых вкладках")
    @DisplayName("Проверка открытия ссылок на соцсети в новой вкладке")
    @Description("Проверяет, что при клике на ссылки соцсетей открываются новые вкладки.")
    public void testLinks() {

        int newList = mainPage.clickSocialLinksAndWaitForNewTabs();
        assertTrue(newList >= 1, "Links to social networks do not open in a new tab");
    }
}