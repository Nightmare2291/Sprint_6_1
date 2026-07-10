package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class OrderPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Поля формы — первая страница
    @FindBy(xpath = "//input[@placeholder='* Имя']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@placeholder='* Фамилия']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@placeholder='* Адрес: куда привезти заказ']")
    private WebElement addressInput;

    // Поле станции метро
    @FindBy(xpath = "//input[@placeholder='* Станция метро']")
    private WebElement metroInput;

    // Кнопка "Далее"
    @FindBy(xpath = "//button[. = 'Далее']")
    private WebElement nextButton;

    @FindBy(xpath = "//input[@placeholder='* Телефон: на него позвонит курьер']")
    private WebElement phoneInput;

    // Сообщения об ошибках
    @FindBy(xpath = "//div[@class='Input_ErrorMessage__3HvIb']")
    private List<WebElement> errorMessages;

    @FindBy(xpath = "//div[@class='Order_Header__BZXOb' and contains(., 'Для кого самокат')]")
    private WebElement firstPageHeader;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void waitForPageToLoad() {
        wait.until(ExpectedConditions.visibilityOf(firstPageHeader));
        wait.until(ExpectedConditions.elementToBeClickable(metroInput));
    }

    public void fillForm(String firstName, String lastName, String address,
                         String metro, String phone) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        addressInput.sendKeys(address);
        selectMetroStation(metro);
        phoneInput.sendKeys(phone);
    }
    /**
     * Выбирает станцию метро из выпадающего списка.
     * Сначала клик на input → появляется list, в котором ищем div.select-search_select.
     */
    private void selectMetroStation(String stationName) {
        // 1. Клик на поле — открывает список
        wait.until(ExpectedConditions.elementToBeClickable(metroInput)).click();

        // 2. Ждём появления контейнера с опциями (находим его по классу)
        //    Скорее всего, будет что-то вроде div.select-search__menu или div.select-search__dropdown
      wait.until(ExpectedConditions.visibilityOfElementLocated(
               org.openqa.selenium.By.xpath("//div[contains(@class, 'select-search__select')]")
        ));



        String xpath = "//*[contains(text(), '"+stationName+"')]";

        WebElement targetOption = wait.until(ExpectedConditions.elementToBeClickable(
                org.openqa.selenium.By.xpath(xpath)
        ));

        targetOption.click();
    }



    public OrderSecondPage clickNext() {
        nextButton.click();
        OrderSecondPage secondPage = new OrderSecondPage(driver);
        secondPage.waitForPageToLoad();
        return secondPage;
    }

    public List<String> getErrorMessages() {
        return errorMessages.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
