package com.virtru.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * VirtruPage.java - a helper class for managing Virtru page objects.
 * @author  Maciej Kocol
 * @version 1.0
 */
public class VirtruPage extends BasePage {

    // buttons
    @FindAll(@FindBy(xpath="//span[@class='userEmail']"))
    private List<WebElement> emailButtons;
    @FindBy(xpath="//a[starts-with(@class,'btn') and text()='Send me an email']")
    private WebElement sendMeAnEmailButton;

    // messages
    @FindBy(xpath="//*[@class='login-message']")
    private WebElement loginMessage;
    @FindBy(xpath="//*[@class='sub-text' and @style]")
    private WebElement loginSubMessage;
    @FindBy(xpath="//div[@class='preview email']//div[contains(@class,'preview-body')]//span/div[.]")
    private WebElement decryptedMessage;

    // titles
    public String pageTitle = "Secure Reader | Virtru";

    public VirtruPage(WebDriver driver) throws InterruptedException{
        super(driver);
    }

    /**
     * This method selects the button with associated email.
     * @param email Email as name of the button to click
     */
    public void selectYourEmail(String email){
        wait.until(d -> emailButtons.size() > 0);
        for (WebElement button : emailButtons) {
            if (button.getText().toLowerCase().contains(email.toLowerCase())) {
                button.click();
                break;
            }
        }
    }

    /**
     * This method gets the main message on page associated with verifying identify for Virtru validation.
     * @return Main text on page
     */
    public String getLoginMessage(){
        wait.until(d -> loginMessage.isDisplayed());
        String message = loginMessage.getText().replaceAll("(\\r|\\n|\\r\\n)+", " ");
        return message;
    }

    /**
     * This method gets the sub-message on page associated with verifying identify for Virtru validation.
     * @return Sub text on page
     */
    public String getLoginSubMessage(){
        wait.until(d -> loginSubMessage.isDisplayed());
        String message = loginSubMessage.getText().replaceAll("(\\r|\\n|\\r\\n)+", " ");
        return message;
    }

    /**
     * This method retreives the decrypted message.
     * @return Decrypted message
     */
    public String getDecryptedMessage(){
        wait.until(d -> decryptedMessage.isDisplayed());
        String message = decryptedMessage.getText().replaceAll("(\\r|\\n|\\r\\n)+", " ");
        return message;
    }

    /**
     * This method selects the option to email the user.
     */
    public void sendMeAnEmail(){
        wait.until(d -> sendMeAnEmailButton.isDisplayed());
        sendMeAnEmailButton.click();
        wait.until(d -> loginSubMessage.isDisplayed());
    }

    /**
     * This method gets the current date in '(month) (day)(suffix)' format (e.g. July 26th).
     * @return Date as string
     */
    public String getDate(){
        Calendar cal = Calendar.getInstance();
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return month + " " + Integer.toString(day) + getDayOfMonthSuffix(day);
    }

    /**
     * This method gets the suffic for the day part of the date (e.g. 26 becomes 26th).
     * @return Suffix for date as string
     */
    public String getDayOfMonthSuffix(final int n) {
        checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }
}