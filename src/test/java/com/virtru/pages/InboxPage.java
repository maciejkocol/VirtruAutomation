package com.virtru.pages;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * InboxPage.java - a helper class for managing Gmail's inbox page objects.
 * @author  Maciej Kocol
 * @version 1.0
 */
public class InboxPage extends BasePage {

    // buttons
    @FindBy(xpath="(//table//a[@href and contains(.,'Unlock Message')])[last()]")
    private WebElement unlockMessageButton;
    @FindBy(xpath="(//table//a[@href and contains(.,'VERIFY ME')])[last()]")
    private WebElement verifyMeButton;
    @FindBy(xpath="(//div[@role='button' and @data-tooltip='Show trimmed content'])[last()]")
    private WebElement showTrimmedContentButton;
    @FindBy(xpath="//div[@aria-label='Refresh']")
    private WebElement refreshInboxButton;

    // links
    @FindBy(xpath="//a[starts-with(@title,'Inbox')]")
    private WebElement inboxLink;

    // emails
    @FindAll(@FindBy(xpath="//span[@class='bog'][text()] | //span[@class='bog']/b[text()]"))
    private List<WebElement> emailResults;
    @FindAll(@FindBy(xpath="//span[@class='bog']/b[text()]"))
    private List<WebElement> newEmailResults;

    // filters
    private String minutesFilter = "(0 minutes ago)";

    // labels
    @FindBy(xpath="(//div[@class='gs']//span[contains(text(),'minutes ago')])[last()]")
    private WebElement minutesAgoLabel;

    // titles
    public String pageTitle = "%s - %s@gmail.com - Gmail";

    public InboxPage(WebDriver driver) throws InterruptedException{
        super(driver);
        waitForPageToLoad(inboxLink);
    }

    /**
     * This method gets the text of the inbox link (e.g. 'Inbox (1)')
     * @return Inbox link as string
     */
    public String getInboxText(){
        wait.until(d -> inboxLink.isDisplayed());
        return inboxLink.getText();
    }

    /**
     * This method clicks on the unlock message button, if available.
     * @throws InterruptedException
     */
    public void unlockMessage() throws InterruptedException{
        Thread.sleep(sleepTime / 4);
        if (!unlockMessageButton.isDisplayed()) {
            showTrimmedContent();
        }
        Thread.sleep(sleepTime / 4);
        wait.until(d -> unlockMessageButton.isDisplayed());
        unlockMessageButton.click();
        Thread.sleep(sleepTime);
    }

    /**
     * This method clicks on the verify me button, if available.
     * @throws InterruptedException
     */
    public void verifyMe() throws InterruptedException{
        Thread.sleep(sleepTime / 4);
        if (!verifyMeButton.isDisplayed()) {
            showTrimmedContent();
        }
        Thread.sleep(sleepTime / 4);
        wait.until(d -> verifyMeButton.isDisplayed());
        verifyMeButton.click();
        Thread.sleep(sleepTime);
    }

    /**
     * This method clicks on the trimmed content to expand email content.
     * @throws InterruptedException
     */
    public void showTrimmedContent() throws InterruptedException{
        Thread.sleep(sleepTime / 4);
        showTrimmedContentButton.click();
    }

    /**
     * This method clicks on the inbox link.
     * @throws InterruptedException
     */
    public void goToInbox() throws InterruptedException{
        wait.until(d -> inboxLink.isDisplayed());
        inboxLink.click();
        Thread.sleep(sleepTime / 4);
    }

    /**
     * This method opens an email with a given subject.
     */
    public void openEmail(String subject) {
        for (WebElement email : emailResults) {
            if (email.getText().toLowerCase().contains(subject.toLowerCase())) {
                email.click();
                break;
            }
        }
    }

    /**
     * This method waits for a new email with a given subject.
     * @throws InterruptedException
     */
    public void waitForNewEmail(String subject) throws InterruptedException{
        outerloop:
        for (int i = 0; i < 10; i++) {
            try{
                for (WebElement email : newEmailResults) {
                    if (email.getText().toLowerCase().contains(subject.toLowerCase())) {
                        Thread.sleep(sleepTime / 4);
                        break outerloop;
                    }
                }
            } catch(StaleElementReferenceException e){
                e.printStackTrace();
            }
            Thread.sleep(sleepTime);
        }
    }
}