package org.example.bonusProgram;

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
    public void testBonusCard(){
        bonus.FillBonusForm();
        assertTrue(bonus.isBonusFormSent(),"The form has not been submitted");
    }
}
