package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OrderPage {

    private WebDriver driver;

    // === Элементы формы ===

    @FindBy(xpath = "//input[@placeholder='* Имя']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@placeholder='* Фамилия']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@placeholder='* Адрес: куда привезти заказ']")
    private WebElement addressInput;

    @FindBy(xpath = "//input[@placeholder='* Станция метро']")
    private WebElement metroStationInput;

    @FindBy(xpath = "//input[@placeholder='* Телефон: на него позвонит курьер']")
    private WebElement phoneInput;

    @FindBy(xpath = "//button[. = 'Далее']")
    private WebElement nextButton;

    // Ошибки (элементы с сообщениями)
    @FindBy(xpath = "//div[@class='Input_ErrorMessage__3HvIb']")
    private List<WebElement> errorMessages;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillForm(String firstName, String lastName, String address,
                         String metro, String phone) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        addressInput.sendKeys(address);
        metroStationInput.sendKeys(metro);
        phoneInput.sendKeys(phone);
    }

    public void clickNext() {
        nextButton.click();
    }

    public List<String> getErrorMessages() {
        return errorMessages.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
