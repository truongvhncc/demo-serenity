package com.automation.autofx.sample_service.models;

import com.automation.autofx.common.utils.CommonUtils;
import net.serenitybdd.screenplay.targets.Target;

public class SearchForm {
    public static String searchFieldTxt;
    public static final Target SEARCH_FIELD;

    public SearchForm() {}

    static {
        searchFieldTxt = (String) CommonUtils.xpathVariable.get("searchFieldTxt");
        SEARCH_FIELD = Target.the("Search field").locatedBy(searchFieldTxt);
    }
}
