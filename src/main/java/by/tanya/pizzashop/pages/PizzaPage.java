package by.tanya.pizzashop.pages;

import by.tanya.pizzashop.utils.ConfigReader;
import by.tanya.pizzashop.utils.Shared;
import by.tanya.pizzashop.utils.Urls;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class PizzaPage extends GeneralPage{

    @FindBy(css = "#primary form select")
    private WebElement sortDropDown;

    @FindBy(css = "#primary div.wc-products")
    private List<WebElement> pizzaCards;

    @FindBy(css = "#min_price")
    private WebElement minPriceInput;

    @FindBy(css = "max_price")
    private WebElement maxPriceInput;

    @FindBy(css = "#woocommerce_price_filter-2 button")
    private WebElement filterbutton;

    @FindBy(css = "#primary a.added_to_cart")
    private WebElement detailsButton;

    @FindBy(css = "#primary .product.type-product.post-425 > .collection_desc > div > a")
    private WebElement addtoCartButton;

    @FindBy(css = "input.qty")
    private WebElement quantityInput;

    public PizzaPage(WebDriver driver){
        super(driver);
    }

    public void open(){
        super.open(Urls.PIZZA);
    }

    public void selectSort(String value){
        waitClickable(sortDropDown);
        Select select =new Select(sortDropDown);
        select.selectByValue(value);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#primary .wc-products")
        ));
        try {
            Thread.sleep(1500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<String> pizzaTitle(){
        waitVisibleList(pizzaCards);
        List<String> titles = new ArrayList<>();
        for(WebElement card:pizzaCards){
            try {
                WebElement titleElement = card.findElement(
                        By.cssSelector("#primary h3"
                        ));
                titles.add(titleElement.getText().strip());
            } catch (NoSuchElementException e){
            }
        }
        return titles;
    }

    public List<Double> pizzaPrices(){
        waitVisibleList(pizzaCards);
        List<Double> prices = new ArrayList<>();
        for(WebElement card :pizzaCards){
            try {
                WebElement priceElement = card.findElement(
                        By.cssSelector(".price span.amount")
                );
                String priceText = priceElement.getText().replace("от","").strip();
                prices.add(Shared.parsePrice(priceText));
            }catch (Exception e){ }
        } return prices;
    }

    public void filterByPrice(int moveMinX, int moveMaxX) {
        scrollToPageElement(sortDropDown);
        waitVisible(filterbutton);

        WebElement minSlider = driver.findElement(By.cssSelector(
                "#woocommerce_price_filter-2 .ui-corner-all span:nth-child(2)"
        ));
        WebElement maxSlider = driver.findElement(By.cssSelector(
                "#woocommerce_price_filter-2 .ui-corner-all span:nth-child(3)"
        ));

        Actions actions = new Actions(driver);
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        actions.moveToElement(minSlider)
                .clickAndHold()
                .moveByOffset(moveMinX, 0)
                .release()
                .perform();
        actions.moveToElement(maxSlider)
                .clickAndHold()
                .moveByOffset(moveMaxX, 0)
                .release()
                .perform();

        filterbutton.click();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(
                "#primary div.wc-products"
        )));
        try { Thread.sleep(1500); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public void addFirstPizzaToCart() {
        waitClickable(addtoCartButton).click();
    }

    public void clickDetailsButton() {
        waitClickable(detailsButton).click();
    }

    public int getCartCount() {
        waitVisible(quantityInput);
        String valueText = quantityInput.getAttribute("value");
        return Integer.parseInt(valueText);
    }
}
