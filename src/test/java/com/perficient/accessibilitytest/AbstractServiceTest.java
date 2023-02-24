package com.perficient.accessibilitytest;

import com.deque.axe.AXE;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public abstract class AbstractServiceTest {

    protected static WebDriver driver;
    protected String browserStackUserName;
    protected String browserStackAccessKey;
    protected static final URL scriptUrl = AbstractServiceTest.class.getResource("/axe.min.js");

    protected void accessibilityTest() {

        JSONObject responseJSON = new AXE.Builder(driver, scriptUrl)
                //.skipFrames()
               // .include("title")
                .analyze();

        JSONArray violations = responseJSON.getJSONArray("violations");
        System.out.println("Violation num: " + violations.length());

        if (violations.length() == 0) {
            assertTrue("No violations found", true);
        } else {
            AXE.writeResults("accessibilityReport", responseJSON);
            assertTrue(AXE.report(violations), false);
        }
    }

    protected void setUp(boolean useCloudEnv, String cloudEnvName,
                         String os, String osVersion, String browserName,
                         String browserVersion, String url) throws IOException {
        if (useCloudEnv == true) {
            if (cloudEnvName.equalsIgnoreCase("browserstack")) {
                getCouldDriver(cloudEnvName, browserStackUserName, browserStackAccessKey, os, osVersion, browserName, browserVersion);
            }
        } else {
            getLocalDriver(browserName);
        }

        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }

    protected void setUp(String browserName, String url) {
        getLocalDriver(browserName);
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    protected WebDriver getLocalDriver(String browserName) {
        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("Safari")) {
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
        }

        return driver;
    }

    protected WebDriver getCouldDriver(String envName, String envUserName, String envAccessKey, String os, String osVersion,
                                       String browserName, String browserVersion) throws IOException {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("browser", browserName);
        cap.setCapability("browser_version", browserVersion);
        cap.setCapability("os", os);
        cap.setCapability("os_version", osVersion);
        if (envName.equalsIgnoreCase("browserstack")) {
            driver = new RemoteWebDriver(new URL("http" + envUserName + ":" + envAccessKey +
                    "@hub-cloud.browserstack.com/wd/hub"), cap);
        }
        return driver;
    }

    protected void moveToChildWindow() {
        Set<String> ids = driver.getWindowHandles();
        Iterator<String> it = ids.iterator();
        String parentWindow = it.next();
        String childWindow = it.next();
        driver.switchTo().window(childWindow);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    protected String getGlobalValue(String value) {
        Properties prop = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("src/main/resources/global.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(value);
    }
}
