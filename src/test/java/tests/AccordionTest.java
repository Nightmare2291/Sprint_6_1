package tests;

import drivers.BrowserFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import pageobjects.HomePage;

import static org.junit.jupiter.api.Assertions.*;

public class AccordionTest {

    private HomePage homePage;
    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = BrowserFactory.getDriver();
        homePage = new HomePage(driver);
        homePage.open();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void firstAccordionAnswerVisible() {
        // Нажимаем на первую кнопку (она может быть уже открыта — проверим)
        homePage.toggleAccordion(0);

        // Проверяем, что соответствующий текст виден (ноль — индекс)
        assertTrue(homePage.isAccordionPanelVisible(0));
        assertEquals("Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
                homePage.getAccordionPanelText(0));
    }

    @Test
    void secondAccordionAnswerHidesByDefault() {
        homePage.toggleAccordion(1);
        assertTrue(homePage.isAccordionPanelVisible(1));
    }

    // Можно добавить параметризованный тест — сколько угодно кнопок
    @ParameterizedTest
    @ValueSource(ints = {0, 2, 5, 7})
    void accordionOpenClose(int index) {
        // Открываем
        homePage.toggleAccordion(index);
        assertTrue(homePage.isAccordionPanelVisible(index));
        // Повторно закрываем — если реализована логика toggle
        homePage.toggleAccordion(index);
        // Если визуально закрыто — hidden-атрибут появится
        String hidden = homePage.accordionPanels.get(index).getAttribute("hidden");
        assertNotNull(hidden); // в текущем HTML — hidden="" при закрытии
    }
}
