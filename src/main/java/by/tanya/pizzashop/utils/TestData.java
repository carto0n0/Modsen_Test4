package by.tanya.pizzashop.utils;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TestData {

    private static final Faker faker = new Faker(new Locale("ru", "RU"));

    public static final String FIRST_NAME = faker.name().firstName();
    public static final String LAST_NAME = faker.name().lastName();

    private static final int daysAhead = faker.number().numberBetween(2, 10);
    private static final LocalDate futureDate = LocalDate.now().plusDays(daysAhead);
    private static final DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final String DATE = futureDate.format(displayFormatter);

    public static final String COUNTRY = "Belarus";
    public static final String ADDRESS = faker.address().streetAddress();
    public static final String CITY = faker.address().city();
    public static final String REGION = faker.address().state();
    public static final String POSTCODE = faker.address().zipCode();
    public static final String PHONE = "+7" + faker.number().numberBetween(9000000000L, 9999999999L);

    public static final String USER_EMAIL = "anisenkot7@gmail.com";
    public static final String USER_PASSWORD = "qwertyuiop";
    public static final String COUPON = "GIVEMEHALYAVA";
    public static final String TEST_IMAGE_PATH = "src/main/resources/TestData/TestImg.jpg";
}
