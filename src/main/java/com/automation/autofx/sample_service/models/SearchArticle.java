package com.automation.autofx.sample_service.models;

import net.serenitybdd.screenplay.targets.Target;

public class SearchArticle {
    public static final Target BODY =  Target.the("article identifier").locatedBy("//article");

    public SearchArticle() {}
}
