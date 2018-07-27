package com.virtru.pages;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * LoginPage.java - a helper class for managing Gmail login page objects.
 * @author  Maciej Kocol
 * @version 1.0
 */
public class LoginPage extends BasePage{

    // fields
    @FindBy(xpath="//input[@type='email']")
    private WebElement userField;
    @FindBy(xpath="//input[@type='password']")
    private WebElement passField;

    // labels
    @FindBy(xpath="//content[text()='Sign in']")
    private WebElement signInLabel;
    @FindBy(xpath="//content[text()='Welcome']")
    private WebElement welcomeLabel;

    // buttons
    @FindBy(xpath="//span[text()='Next']")
    private WebElement nextButton;

    // credentials
    private String user = "";
    private String pass = "";

    // titles
    public String pageTitle = "Gmail";

    public LoginPage(WebDriver driver, String user, String pass){
        super(driver);
        this.user = user;
        this.pass = pass;
    }

    /**
     * This method logs a user into Gmail.
     */
    public void login() throws InterruptedException {
        wait.until(d -> getPageTitle().contains(pageTitle));
        fillUserForm();
        fillPassForm();
    }

    /**
     * This method fills out the user form.
     */
    public void fillUserForm() throws InterruptedException {
        wait.until(d -> userField.isDisplayed());
        wait.until(d -> userField.isEnabled());
        fillForm(userField, user);
    }

    /**
     * This method fills out the password form.
     */
    public void fillPassForm() throws InterruptedException {
        wait.until(d -> passField.isDisplayed());
        wait.until(d -> passField.isEnabled());
        fillForm(passField, pass);
    }

    /**
     * This method fills out a form.
     */
    public void fillForm(WebElement element, String keys) throws InterruptedException {
        Boolean staleElement = true;
        while(staleElement){
            try{
                element.clear();
                element.sendKeys(keys);
                nextButton.click();
                staleElement = false;

            } catch(StaleElementReferenceException e){
                staleElement = true;
            }
            Thread.sleep(sleepTime);
        }
    }
}
