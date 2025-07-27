package com.automation.autofx.sample_service.tasks;

import com.automation.autofx.sample_service.models.MainPage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import org.openqa.selenium.Keys;

public class LookForInformation {
    public static Performable about(String searchTerm) {
        return Task.where("{0} searches for '" + searchTerm + "'",
                Enter.theValue(searchTerm)
                        .into(MainPage.SEARCH_FIELD)
                        .thenHit(Keys.ENTER)
        );
    }
}
