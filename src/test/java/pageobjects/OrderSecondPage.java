package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrderSecondPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//div[@class='Order_Header__BZXOb' and contains(., 'Про аренду')]")
    private WebElement secondPageHeader;

    @FindBy(xpath = "//input[@placeholder='* Когда привезти самокат']")
    private WebElement deliveryDateInput;

    @FindBy(xpath = "//div[@class='Dropdown-control']")
    private WebElement rentalDurationDropdown;

    @FindBy(xpath = "//*[text()='Про аренду']")
    private WebElement rentalTitle;

    @FindBy(xpath = "//div[@class='Dropdown-option']//div")
    private List<WebElement> rentalDurationOptions;

    @FindBy(xpath = "//input[@type='checkbox']")
    private List<WebElement> colorCheckboxes;

    @FindBy(xpath = "//input[@placeholder='Комментарий для курьера']")
    private WebElement commentInput;

    @FindBy(xpath = "//button[. = 'Назад']")
    private WebElement backButton;

    @FindBy(xpath = "//*[@id='root']/div/div[2]/div[3]/button[2]")
    private WebElement submitButton;

    // Модальное окно после клика на "Заказать"
    @FindBy(xpath = "//div[@class='Order_Modal__YZ-d3']")
    private WebElement confirmationModal;

    @FindBy(xpath = "//div[@class='Order_ModalHeader__3FDaJ']")
    private WebElement confirmationTitle;

    @FindBy(xpath = "//div[@class='Order_Modal__YZ-d3']//button[. = 'Да']")
    private WebElement confirmYesButton;

    @FindBy(xpath = "//div[@class='Order_Modal__YZ-d3']//button[. = 'Нет']")
    private WebElement confirmNoButton;

    @FindBy(xpath = "//div[@class='Order_Overlay__3KW-T']")
    private WebElement modalOverlay;

    public OrderSecondPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void waitForPageToLoad() {
        wait.until(ExpectedConditions.visibilityOf(secondPageHeader));
    }

    public boolean isPageLoaded() {
        return secondPageHeader.isDisplayed();
    }

    public void selectDeliveryDate(String date) {
        wait.until(ExpectedConditions.elementToBeClickable(deliveryDateInput));
        deliveryDateInput.sendKeys(date);
    }

    public void selectRentalDuration(String duration) {
        rentalTitle.click();
        rentalDurationDropdown.click();
   //     wait.until(ExpectedConditions.visibilityOfAllElements(rentalDurationOptions));
        /*for (WebElement option : rentalDurationOptions) {
            if (option.getText().contains(duration)) {
                option.click();

                break;
            }
        }*/
        WebElement targetOption = wait.until(ExpectedConditions.elementToBeClickable(
                org.openqa.selenium.By.xpath("//*[contains(text(), '"+duration+"')]")
        ));
        targetOption.click();
    }

    public void selectColor(String colorName) {

        WebElement targetOption = wait.until(ExpectedConditions.elementToBeClickable(
                org.openqa.selenium.By.xpath("//*[contains(text(), '"+colorName+"')]")
        ));
        targetOption.click();
    }

    public void setComment(String comment) {
        wait.until(ExpectedConditions.visibilityOf(commentInput));
        commentInput.sendKeys(comment);
    }

    public void clickBack() {
        backButton.click();
        wait.until(ExpectedConditions.invisibilityOf(secondPageHeader));
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public boolean isConfirmationModalVisible() {
        wait.until(ExpectedConditions.visibilityOf(modalOverlay));
        return confirmationTitle.isDisplayed();
    }

    public String getConfirmationMessage() {
        return confirmationTitle.getText();
    }

    public void clickConfirmYes() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton));
        confirmYesButton.click();
    }

}
