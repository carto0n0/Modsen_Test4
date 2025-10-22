package org.example.bonusProgram;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.example.base.BaseTest;
import org.junit.jupiter.api.*;
import org.example.domain.BonusProgramPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BonusProgramPageTest extends BaseTest {

    private BonusProgramPage bonus;


    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        bonus = new BonusProgramPage(driver);
        bonus.open();
    }

    @Test
    @Order(1)
    @Description("Fill the bonus form and verify it was submitted successfully")
    public void testBonusCard(){
        Allure.step("Fill the bonus form", bonus::FillBonusForm);
        Allure.step("Verify that the form was submitted", () ->
                assertTrue(bonus.isBonusFormSent(), "The form has not been submitted")
        );
    }
}
