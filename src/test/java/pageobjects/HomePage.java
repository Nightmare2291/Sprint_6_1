package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriverWait wait;
    private WebDriver driver;

    // === Элементы ===

    // Кнопка "Заказать" в шапке (верхняя)
    @FindBy(xpath = "//div[@class='Header_Nav__AGCXC']//button[1]")
    private WebElement headerOrderButton;

    // Кнопка "Заказать" внизу (в блоке "Как это работает")
    @FindBy(xpath = "//div[@class='Home_FinishButton__1_cWm']//button")
    private WebElement footerOrderButton;

    // Кнопки аккордеона (вопросы)
    @FindBy(xpath = "//div[@data-accordion-component='AccordionItem']")
    private List<WebElement> accordionButtons;

    // Панели с ответами (все панели, включая скрытые)
    @FindBy(xpath = "//div[@data-accordion-component='AccordionItemPanel']")
    private List<WebElement> accordionPanelsRaw;

    // Логотип Самоката (в шапке)
    @FindBy(xpath = "//a[@class='Header_LogoScooter__3lsAR']")
    private WebElement scooterLogo;

    // Логотип Яндекса
    @FindBy(xpath = "//a[@class='Header_LogoYandex__3TSOI']")
    private WebElement yandexLogo;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
        WebElement title = wait.until(ExpectedConditions.elementToBeClickable(org.openqa.selenium.By.xpath("//*[@id='rcc-confirm-button']")));
        title.click();
        WebElement title1 = wait.until(ExpectedConditions.elementToBeClickable(org.openqa.selenium.By.xpath("//*[@id='root']/div/div/div[5]/div[1]")));
        title1.click();
        String tmp = "//div[@aria-controls='accordion__panel-"+index+"']";
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(org.openqa.selenium.By.xpath(tmp)));
        button.click();
    }

    public String getAccordionPanelText(int index) {
        return accordionPanelsRaw.get(index).getText();
    }

    public boolean isAccordionPanelVisible(int index) {
        String attribute = accordionPanelsRaw.get(index).getAttribute("hidden");
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
