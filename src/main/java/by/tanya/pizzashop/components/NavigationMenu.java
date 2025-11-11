package by.tanya.pizzashop.components;

import by.tanya.pizzashop.pages.GeneralPage;
import by.tanya.pizzashop.utils.Urls;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavigationMenu extends GeneralPage {

    @FindBy(css = "#menu-item-389 > a")
    private WebElement menu;

    @FindBy(css = "#menu-item-390 > a")
    private WebElement menuPizza;

    @FindBy(css = "#menu-item-391 > a")
    private WebElement menudesert;

    @FindBy(css = "#menu-item-393 > a")
    private WebElement menuDrink;

    @Step("Открыть главную страницу")
    public NavigationMenu open() {
        super.open(Urls.BASE);
        return this;
    }

    public NavigationMenu(WebDriver driver) {
        super(driver);
    }

    @Step("Навести курсор на меню")
    public NavigationMenu hoverOnMenu() {
        hover(menu);
        return this;
    }

    @Step("Нажать на раздел пиццы")
    public NavigationMenu сlickOnPizza() {
        safeClick(menuPizza);
        return this;
    }

    @Step("Нажать на раздел дессерты")
    public NavigationMenu сlickOndesert() {
        safeClick(menudesert);
        return this;
    }

    @Step("Нажать на раздел напитки")
    public NavigationMenu сlickOnDrink() {
        safeClick(menuDrink);
        return this;
    }
}
