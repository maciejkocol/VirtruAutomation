package com.virtru.test;

import com.virtru.pages.LoginPage;
import com.virtru.pages.VirtruPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.virtru.pages.InboxPage;

/**
 * InboxTest.java - a simple class for testing the Virtru message decryption via Gmail.
 * @author  Maciej Kocol
 * @version 1.0
 * @see InboxPage
 */
public class InboxTest extends BaseTest {
    InboxPage inboxPage;
    VirtruPage virtruPage;

    @BeforeMethod
    @Parameters({"user", "pass"})

    public void _setup(@Optional("") String user, @Optional("") String pass) throws Exception{

        // Navigate to Gmail
        driver.get("https://mail.google.com");

        // Login
        loginPage = new LoginPage(driver, user, pass);
        loginPage.login();

        // Verify page title
        Assert.assertTrue(loginPage.getPageTitle().contains(loginPage.pageTitle));
    }

    /**
     * This test case will navigate to https://mail.google.com
     * Verify decrypted email body
     * @throws Exception
     */
    @Test(priority = 0)
    @Parameters({"user", "pass"})
    public void test_Decrypted_Email_Body(@Optional("") String user, @Optional("") String pass) throws Exception{

        // Create Inbox page object
        inboxPage = new InboxPage(driver);

        // Create Virtru page object
        virtruPage = new VirtruPage(driver);

        // Verify inbox page title
        Assert.assertEquals(inboxPage.getPageTitle(), String.format(inboxPage.pageTitle, inboxPage.getInboxText(), user));

        // Go to inbox
        inboxPage.goToInbox();

        // Open email by subject
        inboxPage.openEmail("Candidate Test");

        // Click unlock message button
        inboxPage.unlockMessage();

        // Switch to Virtru page
        virtruPage.switchToWindow(virtruPage.pageTitle);

        // Verify Virtru page title
        Assert.assertEquals(virtruPage.getPageTitle(), virtruPage.pageTitle);

        // Verify button presence
        Assert.assertEquals(virtruPage.getLoginMessage(), "Select your email");

        // Select your email button
        virtruPage.selectYourEmail(user + "@gmail.com");

        // Verify message on page
        Assert.assertEquals(virtruPage.getLoginMessage(), "How should we verify your identity?");

        // Click send me an email button
        virtruPage.sendMeAnEmail();

        // Verify message on page
        Assert.assertEquals(virtruPage.getLoginMessage(), "Check your inbox for the verification email");

        // Verify sub message on page
        Assert.assertEquals(virtruPage.getLoginSubMessage(),
                "We sent a message to " + user +
                "@gmail.com with the subject \"Verify with Virtru on " + virtruPage.getDate() +
                "\" to verify your identity."
        );

        // Close Virtru page
        virtruPage.closeWindow(virtruPage.pageTitle);

        // Switch to inbox page
        inboxPage.switchToWindow(inboxPage.pageTitle);

        // Go to inbox
        inboxPage.goToInbox();

        // Wait for email by subject
        inboxPage.waitForNewEmail("Verify with Virtru on " + virtruPage.getDate());

        // Open email by subject
        inboxPage.openEmail("Verify with Virtru on " + virtruPage.getDate());

        // Click verify me button
        inboxPage.verifyMe();

        // Switch to Virtru page
        inboxPage.switchToWindow(virtruPage.pageTitle);

        // Verify decrypted email message
        Assert.assertEquals(virtruPage.getDecryptedMessage(), "This Virtru test is awesome!");

    }
}
