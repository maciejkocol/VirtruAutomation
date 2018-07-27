package com.virtru.pages;

import java.io.File;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.FindBy;
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

    /**
     * This method captures and saves a video of the current test run.
     * @param captureName as the name to save video with
     */
    public void captureSreenshot(String captureName) {
        try {
            String filePath = capturePath + "/" + captureDate + "_" + captureTest + "_" + captureName + "." + captureType;
            createPath(filePath);
            TakesScreenshot scrShot = ((TakesScreenshot)driver);
            File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            FileHandler.copy(srcFile, destFile);
        } catch (Exception ex) {
            System.err.println("Unable to capture screenshot...");
            ex.printStackTrace();
        }
    }

    /**
     * This method creates the path for capturing results.
     * @param filePath as the path to save under
     */
    public void createPath(String filePath) {
        try {
            File outFile = new File(filePath);
            outFile.getParentFile().mkdirs();
            if (!outFile.exists()) outFile.createNewFile();
        } catch (Exception ex) {
            System.err.println("Unable to create path...");
            ex.printStackTrace();
        }
    }
}