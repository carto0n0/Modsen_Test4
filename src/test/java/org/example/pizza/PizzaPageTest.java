package org.example.pizza;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
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
    @Description("Verify sorting pizzas by default menu order")
    public void testSortByDefault(){
        Allure.step("Select default sorting", () -> pizzaPage.selectSort("menu_order"));
        Allure.step("Check pizza titles are not empty", () -> {
            List<String> titles = pizzaPage.pizzaTitle();
            assertTrue(!titles.isEmpty(),"Default sorting is not applied or the list is empty");
        });
    }

    @Test
    @Order(2)
    @Description("Verify sorting pizzas by popularity")
    public void testSortByPopularity(){
        Allure.step("Select sorting by popularity", () -> pizzaPage.selectSort("popularity"));
        Allure.step("Check pizza titles are not empty", () -> {
            List<String> titles = pizzaPage.pizzaTitle();
            assertTrue(!titles.isEmpty(),"Sorting by popularity was not applied or the list is empty");
        });
    }

    @Test
    @Order(3)
    @Description("Verify sorting pizzas by newest")
    public void testSortByNewest(){
        Allure.step("Select sorting by newest", () -> pizzaPage.selectSort("date"));
        Allure.step("Check pizza titles are not empty", () -> {
            List<String> titles = pizzaPage.pizzaTitle();
            assertTrue(!titles.isEmpty(),"Sorting by newest was not applied or the list is empty");
        });
    }

    @Test
    @Order(4)
    @Description("Verify sorting pizzas by ascending price")
    public void testSortByPriceAsc(){
        Allure.step("Select sorting by ascending price", () -> pizzaPage.selectSort("price"));
        Allure.step("Check pizza prices are sorted ascending", () -> {
            List<Double> prices = pizzaPage.pizzaPrices();
            boolean sortedAsc = true;
            for (int i = 1; i < prices.size(); i++) {
                if (prices.get(i) < prices.get(i-1)) {
                    sortedAsc = false;
                    break;
                }
            }
            assertTrue(sortedAsc,"Ascending sorting is not applied or the list is empty");
        });
    }

    @Test
    @Order(5)
    @Description("Verify sorting pizzas by descending price")
    public void testSortByPriceDesc(){
        Allure.step("Select sorting by descending price", () -> pizzaPage.selectSort("price-desc"));
        Allure.step("Check pizza prices are sorted descending", () -> {
            List<Double> prices = pizzaPage.pizzaPrices();
            boolean sortedDesc = true;
            for (int i = 1; i < prices.size(); i++) {
                if (prices.get(i) > prices.get(i-1)) {
                    sortedDesc = false;
                    break;
                }
            }
            assertTrue(sortedDesc,"Descending sorting is not applied or the list is empty");
        });
    }

    @Test
    @Order(6)
    @Description("Filter pizzas by price range and verify list is not empty")
    public void testFilterByPrice() {
        Allure.step("Filter pizzas by price range 30-40", () -> pizzaPage.filterByPrice(30, -40));
        Allure.step("Check filtered pizza prices", () -> {
            List<Double> prices = pizzaPage.pizzaPrices();
            assertTrue(!prices.isEmpty(), "After filtering, the list is empty");
            for (Double price : prices) {
                System.out.println("Filtered pizza price: " + price);
            }
        });
    }

    @Test
    @Order(7)
    @Description("Add first pizza to cart and verify cart count")
    public void testAddPizzaToCart() {
        Allure.step("Add first pizza to cart", pizzaPage::addFirstPizzaToCart);
        Allure.step("Click details button", pizzaPage::clickDetailsButton);
        Allure.step("Verify cart count >= 1", () -> {
            int count = pizzaPage.getCartCount();
            assertTrue(count >= 1, "There must be at least one pizza in the cart");
        });
    }
}
