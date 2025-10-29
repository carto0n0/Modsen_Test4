package by.tanya.pizzashop.pizza;

import by.tanya.pizzashop.base.TestResultWatcher;
import io.qameta.allure.*;
import by.tanya.pizzashop.base.BaseTest;
import by.tanya.pizzashop.pages.PizzaPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Каталог пицц")
@Feature("Тестирование функциональность страницы с пиццами")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(TestResultWatcher.class)
public class PizzaPageTest extends BaseTest {

    private PizzaPage pizzaPage;

    @BeforeEach
    public void initPage() {
        pizzaPage = new PizzaPage(driver);
        pizzaPage.open();
    }

    @Test
    @Order(1)
    @Story("Протестировать сортировку по умолчанию")
    @DisplayName("Проверка сортировки по умолчанию")
    @Description("Проверяем, что пиццы отображаются при сортировке по умолчанию")
    public void testSortByDefault() {

        List<String> titles = pizzaPage.selectSort("menu_order").pizzaTitle();
        assertTrue(!titles.isEmpty(), "Default sorting is not applied or the list is empty");

    }

    @Test
    @Order(2)
    @Story("Протестировать сортировку по популярности")
    @DisplayName("Проверка сортировки по популярности")
    @Description("Проверяем, что пиццы отображаются при сортировке по популярности")
    public void testSortByPopularity() {

        List<String> titles = pizzaPage.selectSort("popularity")
                .pizzaTitle();
        assertTrue(!titles.isEmpty(), "Sorting by popularity was not applied or the list is empty");

    }

    @Test
    @Order(3)
    @Story("Протестировать сортировку по новизне")
    @DisplayName("Проверка сортировки по новизне")
    @Description("Проверяем, что пиццы отображаются при сортировке по новизне")
    public void testSortByNewest() {

        List<String> titles = pizzaPage.selectSort("date")
                .pizzaTitle();
        assertTrue(!titles.isEmpty(), "Sorting by newest was not applied or the list is empty");

    }

    @Test
    @Order(4)
    @Story("Протестировать сортировку по увеличению цены")
    @DisplayName("Проверка сортировки по увеличению цены")
    @Description("Проверяем, что пиццы отображаются при сортировке по увеличению цены")
    public void testSortByPriceAsc() {

        List<Double> prices = pizzaPage.selectSort("price")
                .pizzaPrices();
        boolean sortedAsc = true;
        for (int i = 1; i < prices.size(); i++) {
            if (prices.get(i) < prices.get(i - 1)) {
                sortedAsc = false;
                break;
            }
        }
        assertTrue(sortedAsc, "Ascending sorting is not applied or the list is empty");

    }

    @Test
    @Order(5)
    @Story("Протестировать сортировку по уменьшению цены")
    @DisplayName("Проверка сортировки по уменьшению цены")
    @Description("Проверяем, что пиццы отображаются при сортировке по уменьшению цены")
    public void testSortByPriceDesc() {

        List<Double> prices = pizzaPage.selectSort("price-desc")
                .pizzaPrices();
        boolean sortedDesc = true;
        for (int i = 1; i < prices.size(); i++) {
            if (prices.get(i) > prices.get(i - 1)) {
                sortedDesc = false;
                break;
            }
        }
        assertTrue(sortedDesc, "Descending sorting is not applied or the list is empty");

    }

    @Test
    @Order(6)
    @Story("Протестировать фильтр по цене")
    @DisplayName("Проверка фильтра по цене")
    @Description("Проверяем, что пиццы отображаются при фильтре по цене")
    public void testFilterByPrice() {

        List<Double> prices = pizzaPage.filterByPrice(30, -40).pizzaPrices();
        assertTrue(!prices.isEmpty(), "After filtering, the list is empty");

    }

    @Test
    @Order(7)
    @Story("Протестировать добавление пиццы в корзину")
    @DisplayName("Проверка добавления пиццы в корзину")
    @Description("Добавляем пиццу, проверяем, что пицца добавилась в корзину")
    public void testAddPizzaToCart() {

        int count = pizzaPage.addFirstPizzaToCart()
                .clickDetailsButton()
                .getCartCount();
        assertTrue(count >= 1, "There must be at least one pizza in the cart");

    }
}
