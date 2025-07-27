package com.automation.steps.ui.e2e.test_case_02;

import com.automation.autofx.common.tasks.WaitUntilElementEnabled;
import com.automation.autofx.common.tasks.WaitUntilElementValue;
import com.automation.autofx.common.utils.CommonUtils;
import com.automation.autofx.sample_service.models.MainPage;
import com.automation.autofx.sample_service.questions.MainPageInfo;
import com.automation.autofx.sample_service.questions.ModalInfo;
import com.automation.autofx.sample_service.questions.SearchResult;
import com.automation.autofx.sample_service.tasks.ClickOnButton;
import com.automation.autofx.sample_service.tasks.LookForInformation;
import com.automation.autofx.sample_service.tasks.NavigateTo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.ClearCookiesPolicy;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.WebDriver;

public class TestCase02Steps {
    @Managed(driver = "chrome", clearCookies = ClearCookiesPolicy.BeforeEachTest)
    WebDriver webDriverForOperator;

    Actor actor = new Actor("Operator");

    @Given("Operator is researching things on the internet")
    public void researchingThings() {
        actor.can(BrowseTheWeb.with(webDriverForOperator));
        actor.wasAbleTo(NavigateTo.theSearchHomePage());
    }

    @When("Operator clicks on default search button")
    public void clickOnDefaultSearchButton() {
        actor.attemptsTo(ClickOnButton.clickOnDefaultSearchButton());
    }

    @Then("Operator wait for GotIt Button is displayed")
    public void waitForGotItButton() {
        actor.attemptsTo(WaitUntilElementEnabled.performWithInfo(MainPage.GOT_IT_BUTTON));
    }

    @Then("Modal is displayed with title as {string}")
    public void modalIsDisplayedWithTitleAs (String title) {
        actor.attemptsTo(WaitUntilElementValue.isAsExpected(ModalInfo.aboutTitle(), title, String::equals, true));
    }

    @When("Operator clicks to close modal")
    public void closeModal() {
        actor.attemptsTo(ClickOnButton.closeModal());
    }

    @When("Operator looks up {string}")
    public void searchesFor(String key) throws InterruptedException {
        // thông thường, với các dự án lớn, các key này sẽ được lưu ở trong 1 aliasMapping của từng step chứ ko lưu ở trong variable
        String term = CommonUtils.variableProperty.get(key);
        Thread.sleep(2000);
        actor.attemptsTo(LookForInformation.about(term));
    }

    @Then("Operator should see information about {string}")
    public void should_see_information_about(String term) {
        actor.attemptsTo(WaitUntilElementValue.isAsExpected(SearchResult.aboutSearchTxt(), term, String::equals, true));
    }
}
