package com.virtru.test;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import com.virtru.pages.BasePage;
import com.virtru.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

/**
 * BaseTest.java - a base class for initializing webdriver.
 * @author  Maciej Kocol
 * @version 1.0
 * @see BasePage
 */
public class BaseTest {
    protected WebDriver driver;
    LoginPage loginPage;

    @BeforeTest

    public void setup() throws IOException {

        // Set chrome options
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("marionette", true);
        chromeOptions.addArguments("no-sandbox");
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver");

        // Init chrome driver
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @AfterTest

    public void teardown(){
        WebDriver popup = null;
        Iterator<String> windowIterator = driver.getWindowHandles().iterator();
        while(windowIterator.hasNext()) {
            String windowHandle = windowIterator.next();
            popup = driver.switchTo().window(windowHandle);
            popup.close();
        }
        driver.quit();
    }

}
