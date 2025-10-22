package org.example.domain;

import org.example.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DeliveryAndPayment extends GeneralPage {

    @FindBy(css = "li")
    private List<WebElement> listItems;

    public DeliveryAndPayment(WebDriver driver){
        super(driver);
    }

    public void open(){
        super.open(ConfigReader.get("delivery.url"));
    }

    public boolean isMinOrderSumPresent() {

        for (WebElement item : listItems) {
            String text = item.getText();
            if (text != null && text.contains("800")) {
                return true;
            }

            String before = (String) js.executeScript(
                    "return window.getComputedStyle(arguments[0],'::before').getPropertyValue('content');",
                    item);
            if (before != null && before.contains("800")) {
                return true;
            }

            String marker = (String) js.executeScript(
                    "return window.getComputedStyle(arguments[0],'::marker').getPropertyValue('content');",
                    item);
            if (marker != null && marker.contains("800")) {
                return true;
            }
        }

        return false;
    }
}
