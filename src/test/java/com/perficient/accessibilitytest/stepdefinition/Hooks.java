package com.perficient.accessibilitytest.stepdefinition;

import com.perficient.accessibilitytest.AbstractServiceTest;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Hooks extends AbstractServiceTest {

    @Before
    public void navigateToThePage() {
        setUp(getGlobalValue("browserName"), getGlobalValue("qaUrl"));
    }

    @AfterStep
    public void getScreenShot(Scenario scenario) throws IOException {
        Date currentDate = new Date();
        String fileName = currentDate.toString().replace(" ", "-").replace(":", "-");
        if (scenario.isFailed()){
            File filePath = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            byte[] fileContent = FileUtils.readFileToByteArray(filePath);
            scenario.attach(fileContent, "image/png", fileName);
            System.out.println("Screenshot has been attached with report");
        }
    }

    @After
    public void tearDown(){
        driver.quit();
    }
}
