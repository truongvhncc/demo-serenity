package com.automation.autofx.sample_service.tasks;

import com.automation.autofx.sample_service.models.MainPage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

public class ClickOnButton {
    public static Performable clickOnDefaultSearchButton() {
        return Task.where("{0} click on default search button",
                Click.on(MainPage.DEFAULT_SEARCH_BUTTON)
        );
    }

    public static Performable closeModal() {
        return Task.where("{0} click on close modal button",
                Click.on(MainPage.CLOSE_MODAL_BUTTON)
        );
    }
}
