package tests;

import drivers.BrowserFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.HomePage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccordionTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = BrowserFactory.getDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    // Параметризованный тест для всех 8 вопросов
    @ParameterizedTest
    @CsvSource({
            "0, Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "1, Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
            "2, Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
            "3, Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
            "4, Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "5, Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.",
            "6, Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
            "7, Да, обязательно. Всем самокатов! И Москве, и Московской области."
    })

    void allAccordionPanelsHaveCorrectText(int index, String expectedText) {
        HomePage homePage = new HomePage(driver);
        homePage.open();

        // Открываем панель
        homePage.toggleAccordion(index);

        // Проверяем, что панель открыта
        assertTrue(homePage.isAccordionPanelVisible(index),
                "Панель " + index + " должна быть видна после клика");

        // Проверяем текст
        String actualText = homePage.getAccordionPanelText(index).trim();
        assertEquals(expectedText, actualText,
                "Текст панели " + index + " должен совпадать");
    }
}

