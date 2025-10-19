package org.example.utils;

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
        String value = ConfigReader.get(configKey);
        dataInput.sendKeys(value);
    }
}
