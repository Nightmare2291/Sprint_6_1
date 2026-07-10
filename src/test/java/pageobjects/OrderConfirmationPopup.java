package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderConfirmationPopup {

    private WebDriver driver;

    @FindBy(xpath = "//div[@id='root']/div/div[2]/div[5]/div[1]/text()")
    private WebElement successTitle;

    public OrderConfirmationPopup(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getSuccessMessage() {
        return successTitle.getText();
    }

    public boolean isVisible() {
        return successTitle.isDisplayed();
    }
}
