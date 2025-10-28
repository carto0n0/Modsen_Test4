package by.tanya.pizzashop.myAccount;

import by.tanya.pizzashop.base.BaseTest;
import by.tanya.pizzashop.pages.AutorizationPage;
import by.tanya.pizzashop.utils.TestData;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import by.tanya.pizzashop.pages.MyAccountPage;
import by.tanya.pizzashop.utils.ConfigReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyAccountPageTest extends BaseTest {

    private MyAccountPage myAccountPage;

    private AutorizationPage auth;

    @BeforeEach
    public void initPage(){
        myAccountPage = new MyAccountPage(driver);
        auth = new AutorizationPage(driver);
        auth.openAuthPage();
    }

    @Test
    @Order(1)
    @Description("Add a file to the My Account section and verify it was uploaded")
    public void testAddFile(){
        Allure.step("Authorize user", auth::authorize);
        Allure.step("Open My Account info", myAccountPage::openMyAccountInfo);
        Allure.step("Add file", () -> myAccountPage.addFile(TestData.TEST_IMAGE_PATH));
        Allure.step("Verify file was added", () ->
                assertTrue(myAccountPage.isFileAdd(), "The file has not been added")
        );
    }
}
