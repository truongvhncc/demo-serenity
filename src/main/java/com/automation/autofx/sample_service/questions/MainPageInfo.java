package com.automation.autofx.sample_service.questions;

import com.automation.autofx.sample_service.models.MainPage;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class MainPageInfo {
    public MainPageInfo() {}

    public static Question<String> aboutTxtInInput() {
        return Question.about("Text in input").answeredBy(actor -> {
            return (String) actor.asksFor(Text.of(MainPage.SEARCH_FIELD).asAString());
        });
    }
}
