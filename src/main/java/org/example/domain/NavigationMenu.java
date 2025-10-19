package org.example.domain;

import org.example.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavigationMenu extends GeneralPage{

    @FindBy(css = "#menu-item-389 > a")
    private WebElement menu;

    @FindBy(css = "#menu-item-390 > a")
    private WebElement menuPizza;

    @FindBy(css = "#menu-item-391 > a")
    private WebElement menudesert;

    @FindBy(css = "#menu-item-393 > a")
    private WebElement menuDrink;

    public void open() {
        super.open(ConfigReader.get("main.url"));
    }

    public NavigationMenu(WebDriver driver){
        super(driver);
    }

    public void hoverOnMenu(){
        hover(menu);
    }

    public void сlickOnPizza(){
        safeClick(menuPizza);
    }

    public void сlickOndesert(){
        safeClick(menudesert);
    }

    public void сlickOnDrink(){
        safeClick(menuDrink);
    }
}
