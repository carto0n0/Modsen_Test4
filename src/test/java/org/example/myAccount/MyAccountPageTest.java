package org.example.myAccount;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.example.base.BaseTest;
import org.example.domain.AutorizationPage;
import org.example.domain.MyAccountPage;
import org.example.utils.ConfigReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyAccountPageTest extends BaseTest {

    private MyAccountPage myAccountPage;

    private AutorizationPage auth;

    @BeforeEach
    @Override
    public void setUp() throws IOException {
        super.setUp();
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
        Allure.step("Add file", () -> myAccountPage.addFile(ConfigReader.get("file.path")));
        Allure.step("Verify file was added", () ->
                assertTrue(myAccountPage.isFileAdd(), "The file has not been added")
        );
    }
}
