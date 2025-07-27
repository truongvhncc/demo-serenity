package com.automation.autofx.sample_service.questions;

import com.automation.autofx.sample_service.models.MainPage;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class ModalInfo {
    public ModalInfo() {}

    public static Question<String> aboutTitle() {
        return Question.about("Title in modal").answeredBy(actor -> {
           return (String) actor.asksFor(Text.of(MainPage.MODAL_TITLE).asAString());
        });
    }
}
