package com.perficient.accessibility;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.perficient.accessibility.MyHealthToolkitElements.*;

public class MyHealthToolkit {

    @FindBy(xpath = USER_NAME_FIELD)
    private WebElement userName;

    @FindBy(xpath = USER_PASSWORD_FIELD)
    private WebElement userPassword;

    @FindBy(xpath = USER_LOGIN_BUTTON)
    private WebElement loginButton;

    public void enterUserCredentials(String userName, String password){
        this.userName.sendKeys(userName);
        this.userPassword.sendKeys(password);
        System.out.println(userName);
        System.out.println(password);
    }

    public void userClickOnButton(){
        loginButton.click();
    }
}
