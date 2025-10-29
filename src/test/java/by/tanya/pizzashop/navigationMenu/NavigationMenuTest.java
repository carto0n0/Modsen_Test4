package by.tanya.pizzashop.navigationMenu;

import by.tanya.pizzashop.base.BaseTest;
import by.tanya.pizzashop.base.TestResultWatcher;
import io.qameta.allure.Description;
import by.tanya.pizzashop.components.NavigationMenu;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Главная страница")
@DisplayName("Тестирование функциональности меню")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(TestResultWatcher.class)
public class NavigationMenuTest extends BaseTest {

    private NavigationMenu navigationMenu;

    @BeforeEach
    public void initPage() {
        navigationMenu = new NavigationMenu(driver);
        navigationMenu.open();
    }

    @Test
    @Order(1)
    @Story("Проверка переходов между всеми разделами меню")
    @DisplayName("Переход между всеми разделами меню")
    @Description("Проверяет, что при клике на пункты меню происходит переход на правильные страницы: Pizza, Dessert, Drink.")
    public void testNavigateThroughAllSection() {
        navigationMenu.hoverOnMenu()
                .сlickOnPizza();
        assertTrue(driver.getCurrentUrl().contains("/pizza"), "Didn't go to the Pizza section");

        navigationMenu.hoverOnMenu()
                .сlickOndesert();
        assertTrue(driver.getCurrentUrl().contains("/deserts"), "Didn't go to the Dessert section");


        navigationMenu.hoverOnMenu()
                .сlickOnDrink();
        assertTrue(driver.getCurrentUrl().contains("/drinks"), "Didn't go to the Drink section");
    }
}
