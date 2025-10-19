package org.example.domain;

import org.example.utils.Shared;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DeliveryAndPayment extends GeneralPage {

    @FindBy(css = "#post-24 .total > strong > span")
    private WebElement paymentSumBlock;

    public DeliveryAndPayment(WebDriver driver){
        super(driver);
    }

    public double getCurrentPrice() {
        waitVisible(paymentSumBlock);
        return Shared.parsePrice(paymentSumBlock.getText());
    }
}
