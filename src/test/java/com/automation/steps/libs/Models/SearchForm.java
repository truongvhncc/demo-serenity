package com.automation.steps.libs.Models;

import com.automation.steps.common.utils.CommonUtils;
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
