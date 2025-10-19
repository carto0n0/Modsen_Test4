package org.example.pizza;

import org.example.base.BaseTest;
import org.example.domain.PizzaPage;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PizzaPageTest extends BaseTest {

    private PizzaPage pizzaPage;

    @BeforeEach
    public void setUp(){
        super.setUp();
        pizzaPage = new PizzaPage(driver);
        pizzaPage.open();
    }

    @Test
    @Order(1)
    public void testSortByDefault(){
        pizzaPage.selectSort("menu_order");
        List<String> titles = pizzaPage.pizzaTitle();
        assertTrue(!titles.isEmpty(),"default sorting is not applied or the list is empty");
    }

    @Test
    @Order(2)
    public void testSortByPopularity(){
        pizzaPage.selectSort("popularity");
        List<String> titles = pizzaPage.pizzaTitle();
        assertTrue(!titles.isEmpty(),"sorting by priority was not applied or the list is empty");
    }

    @Test
    @Order(3)
    public void testSortByNewest(){
        pizzaPage.selectSort("date");
        List<String> titles = pizzaPage.pizzaTitle();
        assertTrue(!titles.isEmpty(),"sorting by last did not apply or the list is empty");
    }

    @Test
    @Order(4)
    public void testSortByPriceAsc(){
        pizzaPage.selectSort("price");
        List<Double> prices = pizzaPage.pizzaPrices();

        boolean sortedAsc =true;
        for (int i =1; i<prices.size();i++){
            if(prices.get(i)<prices.get(i-1)){
                sortedAsc=false;
                break;
            }
        }
        assertTrue(sortedAsc,"ascending sorting is not applied or the list is empty");
    }

    @Test
    @Order(5)
    public void testSortByPriceDesc(){
        pizzaPage.selectSort("price-desc");
        List<Double> prices = pizzaPage.pizzaPrices();

        boolean sortedDesc =true;
        for (int i =1; i<prices.size();i++){
            if(prices.get(i)>prices.get(i-1)){
                sortedDesc=false;
                break;
            }
        }
        assertTrue(sortedDesc,"descending sorting is not applied or the list is empty");
    }

    @Test
    @Order(6)
    public void testFilterByPrice() {
        pizzaPage.filterByPrice(30, -40);
        List<Double> prices = pizzaPage.pizzaPrices();

        assertTrue(!prices.isEmpty(), "after filtering, the list is empty");

        for (Double price : prices) {
            System.out.println("the list of pizzaPage prices after filtering: " + price);
        }
    }

    @Test
    @Order(7)
    public void testAddPizzaToCart() {
        pizzaPage.addFirstPizzaToCart();
        pizzaPage.clickDetailsButton();
        int count = pizzaPage.getCartCount();

        assertTrue(count >= 1, "There must be at least one pizzaPage");
    }
}
