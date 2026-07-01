package tests;

import drivers.BrowserFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import pageobjects.HomePage;
import pageobjects.OrderPage;
import pageobjects.OrderConfirmationPopup;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderFlowTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = BrowserFactory.getDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvSource({
            "Анна,Иванова,Москва, ул. Ленина,10,Белорусская,89001234567",
            "Иван,Петров,Санкт-Петербург, Невский пр.,50,Площадь Восстания,89998765432"
    })
    void positiveOrderFlow(String firstName, String lastName, String address,
                           String metro, String phone) {

        // Шаг 1: Открыть сайт и нажать кнопку "Заказать" (верхняя)
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickHeaderOrderButton();

        // Шаг 2: Заполнить форму
        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillForm(firstName, lastName, address, metro, phone);

        // Шаг 3: Нажать "Далее"
        orderPage.clickNext();

        // Шаг 4: Ожидаем появление модального окна (или проверка по заголовку)
        // Поскольку у вас "всплывающее окно" — обычно модальное окно
        OrderConfirmationPopup popup = new OrderConfirmationPopup(driver);
        assertTrue(popup.isVisible());
        assertEquals("Заказ оформлен", popup.getSuccessMessage()); // 🛑 проверьте реальный текст!
        // ⚠️ Если баг — может не перейти на след. шаг. Это нормально: тест показал баг.
    }

    // 🧪 БАГ-ТЕСТ: проверка поведения в Chrome при нажатии на кнопку
    @Test
    @Disabled("Known bug in Chrome — кнопка не кликается в автоматическом режиме")
    void chromeOrderButtonBug() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickHeaderOrderButton();
        // Если тест упал здесь — это и есть баг
    }
}
