package com.virtru.pages;

import java.util.Iterator;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * BasePage.java - a helper class for managing shared page objects.
 * @author  Maciej Kocol
 * @version 1.0
 */
public class BasePage {
    public WebDriver driver;
    public WebDriverWait wait;

    public int sleepTime = 1000;

    private String captureDate;
    private String captureTest;
    private String capturePath = "./results";
    private String captureType = "png";
    private String dateFormat = "yyyyMMddHHmmss";

    public BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    /**
     * This method gets the current page title.
     * @return Page title as string
     */
    public String getPageTitle(){
        return driver.getTitle();
    }

    /**
     * This method waits for a given element on page to load.
     */
    public void waitForPageToLoad(WebElement element) throws InterruptedException{
        wait.until(d -> element.isDisplayed());
    }

    /**
     * This method switches to another window by page title.
     * @param pageTitle Page title of the window to switch to
     * @throws InterruptedException
     */
    public void switchToWindow(String pageTitle) throws InterruptedException {
        outerloop:
        for (int i = 0; i < 10; i++) {
            Iterator<String> windowIterator = driver.getWindowHandles().iterator();
            while (windowIterator.hasNext()) {
                String windowHandle = windowIterator.next();
                driver.switchTo().window(windowHandle);
                String currentTitle = driver.getTitle();
                if (currentTitle.contains(pageTitle)) {
                    break outerloop;
                }
            }
        }
    }

    /**
     * This method closes a window by page title.
     * @param pageTitle Page title of the window to close
     * @throws InterruptedException
     */
    public void closeWindow(String pageTitle) throws InterruptedException {
        switchToWindow(pageTitle);
        driver.close();
    }
}