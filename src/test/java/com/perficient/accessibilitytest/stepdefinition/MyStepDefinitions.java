package com.perficient.accessibilitytest.stepdefinition;

import com.perficient.accessibility.MyHealthToolkit;
import com.perficient.accessibilitytest.AbstractServiceTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.PageFactory;

public class MyStepDefinitions extends AbstractServiceTest {

    static MyHealthToolkit myHealthToolkit;

    static void getInItElement() {
        myHealthToolkit = PageFactory.initElements(driver, MyHealthToolkit.class);
    }

    @Given("I navigate to my health toolkit page")
    public void i_navigate_to_my_health_toolkit_page() {
        getInItElement();
        System.out.println("I am in login page");
    }

    @When("I enter my username: {string} and password: {string}")
    public void i_enter_my_username_and_password(String userName, String userPassword) {
        getInItElement();
        myHealthToolkit.enterUserCredentials(userName, userPassword);
    }

    @When("I click on login button")
    public void i_click_on_login_button() {
        getInItElement();
        myHealthToolkit.userClickOnButton();
    }

    @Then("I see accessibility report")
    public void i_see_accessibility_report() {
        accessibilityTest();
    }
}

