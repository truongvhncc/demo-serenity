package com.automation.steps.ui.e2e.test_case_01;

import com.automation.autofx.sample_service.tasks.LookForInformation;
import com.automation.autofx.sample_service.tasks.NavigateTo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.ClearCookiesPolicy;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.WebDriver;

public class TestCase01Steps {
    @Managed(driver = "chrome", clearCookies = ClearCookiesPolicy.BeforeEachTest)
    WebDriver webDriverForOperator;

    Actor actor = new Actor("operator");

    @Given("Operator is researching things on the internet")
    public void researchingThings() {
        actor.can(BrowseTheWeb.with(webDriverForOperator));
        actor.wasAbleTo(NavigateTo.theSearchHomePage());
    }

    @When("Operator looks up {string}")
    public void searchesFor(String term) {
        actor.attemptsTo(
                LookForInformation.about(term)
        );
    }

    @Then("Operator should see information about {string}")
    public void should_see_information_about(String term) {
        // do something
    }
}
