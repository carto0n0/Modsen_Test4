package by.tanya.pizzashop.utils;

import org.openqa.selenium.WebElement;

public class Shared {

    public static double parsePrice(String priceText) {
        String txt = priceText.replace("₽", "")
                .replace(",", ".")
                .strip();
        return Double.parseDouble(txt);
    }

    public static void enterData(WebElement dataInput, String configKey) {
        dataInput.clear();
        dataInput.sendKeys(configKey);
    }
}
