package org.example.myAccount;

import org.example.base.BaseTest;
import org.example.domain.AutorizationPage;
import org.example.domain.MyAccountPage;
import org.example.utils.ConfigReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyAccountPageTest extends BaseTest {

    private MyAccountPage myAccountPage;

    private AutorizationPage auth;

    @BeforeEach
    @Override
    public void setUp(){
        super.setUp();
        myAccountPage = new MyAccountPage(driver);
        auth = new AutorizationPage(driver);
        auth.openAuthPage();
    }

    @Test
    @Order(1)
    public void testAddFile(){
        auth.authorize();
        myAccountPage.openMyAccountInfo();
        myAccountPage.addFile(ConfigReader.get("file.path"));
        assertTrue(myAccountPage.isFileAdd(), "the file has not been added");
    }
}
