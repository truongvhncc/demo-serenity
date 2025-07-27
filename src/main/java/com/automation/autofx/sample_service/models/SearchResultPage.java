package com.automation.autofx.sample_service.models;

import com.automation.autofx.common.utils.CommonUtils;
import net.serenitybdd.screenplay.targets.Target;

public class SearchResultPage {
    public static String searchInputTxt;
    public static final Target SEARCH_INPUT;

    public SearchResultPage() {}

    static {
        searchInputTxt = (String) CommonUtils.xpathVariable.get("searchInputTxt");

        SEARCH_INPUT = Target.the("Search input").locatedBy(searchInputTxt);
    }
}
