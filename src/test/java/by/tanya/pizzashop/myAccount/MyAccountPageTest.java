package by.tanya.pizzashop.myAccount;

import by.tanya.pizzashop.base.BaseTest;
import by.tanya.pizzashop.base.TestResultWatcher;
import by.tanya.pizzashop.pages.AutorizationPage;
import by.tanya.pizzashop.utils.TestData;
import io.qameta.allure.Description;
import by.tanya.pizzashop.pages.MyAccountPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Аккаунт")
@DisplayName("Тестирование функциональности аккаунта")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(TestResultWatcher.class)
public class MyAccountPageTest extends BaseTest {

    private MyAccountPage myAccountPage;

    private AutorizationPage auth;

    @BeforeEach
    public void initPage() {
        myAccountPage = new MyAccountPage(driver);
        auth = new AutorizationPage(driver);
        auth.openAuthPage();
    }

    @Test
    @Order(1)
    @Story("Загрузка файла в 'Мой аккаунт'")
    @DisplayName("Добавление файла в аккаунт")
    @Description("Авторизация, перейти в аккаунт, добавить изображение, проверить, что файл успешно загружен.")
    public void testAddFile() {
        auth.authorize();
        myAccountPage.openMyAccountInfo()
                .addFile(TestData.TEST_IMAGE_PATH);

        assertTrue(myAccountPage.isFileAdd(), "The file has not been added");
    }
}
