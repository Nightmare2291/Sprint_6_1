package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {

    private WebDriver driver;

    // === Элементы ===

    // Кнопка "Заказать" в шапке (верхняя)
    @FindBy(xpath = "//div[@class='Header_Nav__AGCXC']//button[1]")
    private WebElement headerOrderButton;

    // Кнопка "Заказать" внизу (в блоке "Как это работает")
    @FindBy(xpath = "//div[@class='Home_FinishButton__1_cWm']//button")
    private WebElement footerOrderButton;

    // === Вопросы о важном (аккордеон) ===
    @FindBy(xpath = "//div[@data-accordion-component='Accordion']//div[@class='accordion__button']")
    private List<WebElement> accordionButtons;

    @FindBy(xpath = "//div[@data-accordion-component='AccordionItemPanel']//p")
    public List<WebElement> accordionPanels;

    // Логотип Самоката (в шапке)
    @FindBy(xpath = "//a[@class='Header_LogoScooter__3lsAR']")
    private WebElement scooterLogo;

    // Логотип Яндекса
    @FindBy(xpath = "//a[@class='Header_LogoYandex__3TSOI']")
    private WebElement yandexLogo;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // === Методы ===

    public void open() {
        driver.get("https://qa-scooter.education-services.ru/");
    }

    public void clickHeaderOrderButton() {
        headerOrderButton.click();
    }

    public void clickFooterOrderButton() {
        footerOrderButton.click();
    }

    // Нажимает на N-ю кнопку аккордеона и проверяет, что соответствующий текст виден
    public void toggleAccordion(int index) {
        accordionButtons.get(index).click();
    }

    public String getAccordionPanelText(int index) {
        return accordionPanels.get(index).getText();
    }

    public boolean isAccordionPanelVisible(int index) {
        String attribute = accordionPanels.get(index).getAttribute("hidden");
        return attribute == null || attribute.isEmpty();
    }

    public String getHomePageTitle() {
        return driver.getTitle();
    }

    public void clickScooterLogo() {
        scooterLogo.click();
    }

    public void clickYandexLogo() {
        yandexLogo.click();
    }
}
