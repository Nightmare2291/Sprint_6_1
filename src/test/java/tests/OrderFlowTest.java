package tests;

import drivers.BrowserFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import pageobjects.*;

import java.util.Arrays;

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
            "Анна,Иванова,Москва ул. Ленина 10,Черкизовская,89001234567,20.05.2025,сутки,чёрный жемчуг,Привезите вовремя",
            "Иван,Петров,Санкт-Петербург Невский пр.50,Сокольники,89998765432,22.05.2025,трое суток,серая безысходность, Отвали"
    })
    void fullOrderFlow(String firstName, String lastName, String address,
                       String metro, String phone, String date, String duration,
                       String color, String comment) {

        // 1. Открыть главную и нажать кнопку заказа
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickHeaderOrderButton();

        // 2. Заполнить первую страницу
        OrderPage firstPage = new OrderPage(driver);
        firstPage.waitForPageToLoad();
        firstPage.fillForm(firstName, lastName, address, metro, phone);

        // 3. Перейти на вторую страницу
        OrderSecondPage secondPage = firstPage.clickNext();
        secondPage.waitForPageToLoad();

        // 4. Заполнить вторую страницу (если параметр передан)
        secondPage.selectDeliveryDate(date);
        secondPage.selectRentalDuration(duration);
        if (color != null && !color.isEmpty()) {
            secondPage.selectColor(color);
        }
        if (comment != null && !comment.isEmpty()) {
            secondPage.setComment(comment);
        }

        // 5. Нажать «Заказать»
        secondPage.clickSubmit();

        // 6. Нажать "Да" на всплывающем окне
        OrderConfirmationPopup popup = new OrderConfirmationPopup(driver);
        //assertTrue(popup.isVisible(), "После заказа должно появиться модальное окно");
        secondPage.clickConfirmYes();

        // Проверяем, что появилось окно с "Заказ оформлен"
        assertTrue(secondPage.isOrderSuccessModalVisible(), "Окно 'Заказ оформлен' должно появиться");
        "Заказ оформлен".contains(secondPage.getOrderSuccessMessage());

        // Опционально: проверяем наличие номера заказа
        assertTrue(secondPage.hasOrderNumber(), "В тексте должен быть номер заказа");

    }
}
