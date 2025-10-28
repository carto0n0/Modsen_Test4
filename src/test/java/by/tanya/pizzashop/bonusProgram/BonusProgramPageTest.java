package by.tanya.pizzashop.bonusProgram;

import by.tanya.pizzashop.base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import by.tanya.pizzashop.pages.BonusProgramPage;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BonusProgramPageTest extends BaseTest {

    private BonusProgramPage bonus;




    @BeforeEach
    public void initPage() {
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
