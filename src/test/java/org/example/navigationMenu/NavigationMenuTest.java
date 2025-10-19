package org.example.navigationMenu;

import org.example.base.BaseTest;
import org.example.domain.NavigationMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationMenuTest extends BaseTest {

    private NavigationMenu navigationMenu;

    @BeforeEach
    @Override
    public void setUp(){
        super.setUp();
        navigationMenu = new NavigationMenu(driver);
        navigationMenu.open();
    }

    @Test
    @Order(1)
    public void testNavigateThroughAllSection(){
        navigationMenu.hoverOnMenu();
        navigationMenu.сlickOnPizza();
        assertTrue(driver.getCurrentUrl().contains("/pizza"),"didn't go to the section pizza");

        navigationMenu.hoverOnMenu();
        navigationMenu.сlickOndesert();
        assertTrue(driver.getCurrentUrl().contains("/deserts"), "didn't go to the section desserts");

        navigationMenu.hoverOnMenu();
        navigationMenu.сlickOnDrink();
        assertTrue(driver.getCurrentUrl().contains("/drinks"), "didn't go to the section");
    }
}
