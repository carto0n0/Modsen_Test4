package by.tanya.pizzashop.bonusProgram;

import by.tanya.pizzashop.base.BaseTest;
import by.tanya.pizzashop.base.TestResultWatcher;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import by.tanya.pizzashop.pages.BonusProgramPage;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Бонусная программа")
@DisplayName("Тестирование функционала бонусной программы")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(TestResultWatcher.class)
public class BonusProgramPageTest extends BaseTest {

    private BonusProgramPage bonus;

    @BeforeEach
    public void initPage() {
        bonus = new BonusProgramPage(driver);
        bonus.open();
    }

    @Test
    @Order(1)
    @DisplayName("Отправка заявки на бонусную карту")
    @Story("Заполнить форму для бонусной карты")
    @Description("Заполнить форму бонусной программы и проверить, что появляется алерт об успешной отправке заявки")
    public void testBonusCard() {
        boolean sent = bonus
                .fillBonusForm()
                .waitForAlert()
                .isBonusFormSent();

        assertTrue(sent, "The form has not been submitted");
    }
}
