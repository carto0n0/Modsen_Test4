package by.tanya.pizzashop.navigationMenu;

import by.tanya.pizzashop.base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import by.tanya.pizzashop.components.NavigationMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationMenuTest extends BaseTest {

    private NavigationMenu navigationMenu;

    @BeforeEach
    public void initPage(){
        navigationMenu = new NavigationMenu(driver);
        navigationMenu.open();
    }

    @Test
    @Order(1)
    @Description("Navigate through all menu sections and verify the correct URLs")
    public void testNavigateThroughAllSection(){
        Allure.step("Go to Pizza section", () -> {
            navigationMenu.hoverOnMenu();
            navigationMenu.сlickOnPizza();
            assertTrue(driver.getCurrentUrl().contains("/pizza"), "Didn't go to the Pizza section");
        });

        Allure.step("Go to Dessert section", () -> {
            navigationMenu.hoverOnMenu();
            navigationMenu.сlickOndesert();
            assertTrue(driver.getCurrentUrl().contains("/deserts"), "Didn't go to the Dessert section");
        });

        Allure.step("Go to Drink section", () -> {
            navigationMenu.hoverOnMenu();
            navigationMenu.сlickOnDrink();
            assertTrue(driver.getCurrentUrl().contains("/drinks"), "Didn't go to the Drink section");
        });
    }
}
