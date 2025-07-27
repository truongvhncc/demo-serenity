package com.automation.steps.ui.e2e.test_case_02;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"classpath:features/e2e/check_ui.feature"},
        plugin = {"pretty"},
        glue = {"com.automation.steps.ui.e2e.test_case_02"
        }
)
public class TestCase02CucumberTest {
}
