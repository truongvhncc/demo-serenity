package com.automation.steps.ui.e2e.test_case_01;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"classpath:features/e2e/search_by_keyword_green.feature"},
        plugin = {"pretty"},
        glue = {"com.automation.steps.ui.e2e.test_case_01"
        }
)
public class TestCase01CucumberTest {
}
