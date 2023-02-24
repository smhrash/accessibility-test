package com.perficient.accessibilitytest.runnertest;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"com/perficient/accessibilitytest/stepdefinition"},
        plugin = {"json:target/cucumber-report.json"},
        tags = "@accessibilityReport"
)
public class RunnerTest extends AbstractTestNGCucumberTests {
}
