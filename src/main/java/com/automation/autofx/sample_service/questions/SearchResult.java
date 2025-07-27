package com.automation.autofx.sample_service.questions;

import com.automation.autofx.sample_service.models.SearchResultPage;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Attribute;

public class SearchResult {
    public SearchResult() {}

    public static Question<String> aboutSearchTxt() {
        return Question.about("Text in search input").answeredBy(actor -> {
            return (String) actor.asksFor(Attribute.of(SearchResultPage.SEARCH_INPUT).named("value").asAString());
        });
    }
}
