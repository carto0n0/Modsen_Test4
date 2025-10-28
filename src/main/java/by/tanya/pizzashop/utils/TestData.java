package by.tanya.pizzashop.utils;

import com.github.javafaker.Faker;
import java.util.Locale;

public class TestData {

    private static final Faker faker = new Faker(new Locale("ru", "RU"));

    public static final String FIRST_NAME = faker.name().firstName();
    public static final String LAST_NAME = faker.name().lastName();
    public static final String DATE = faker.date().birthday(18, 60).toString();
    public static final String COUNTRY = "Belarus";
    public static final String ADDRESS = faker.address().streetAddress();
    public static final String CITY = faker.address().city();
    public static final String REGION = faker.address().state();
    public static final String POSTCODE = faker.address().zipCode();
    public static final String PHONE = faker.phoneNumber().cellPhone();

    public static final String COUPON = "GIVEMEHALYAVA";
    public static final String TEST_IMAGE_PATH = "src/test/resources/TestData/TestImg.jpg";
}
