package by.tanya.pizzashop.utils;

public class Urls {
    public static final String BASE = ConfigReader.get("base.url");
    public static final String CART = BASE + "/cart/";
    public static final String PIZZA = BASE + "/product-category/menu/pizza/";
    public static final String ACCOUNT = BASE + "/my-account/";
    public static final String ORDER = BASE + "/checkout/";
    public static final String BONUS = BASE + "/bonus/";
    public static final String DELIVERY = BASE + "/delivery/";
}

