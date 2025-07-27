package com.automation.autofx.sample_service.models;

import com.automation.autofx.common.utils.CommonUtils;
import net.serenitybdd.screenplay.targets.Target;

public class MainPage {
    public static String defaultSearchButtonTxt;
    public static String searchFieldTxt;
    public static String gotItTxt;
    public static String modalTitleTxt;
    public static String closeButtonTxt;
    public static final Target SEARCH_FIELD;
    public static final Target DEFAULT_SEARCH_BUTTON;
    public static final Target GOT_IT_BUTTON;
    public static final Target MODAL_TITLE;
    public static final Target CLOSE_MODAL_BUTTON;

    public MainPage() {}

    static {
        searchFieldTxt = (String) CommonUtils.xpathVariable.get("searchFieldTxt");
        defaultSearchButtonTxt = (String) CommonUtils.xpathVariable.get("defaultSearchButtonTxt");
        gotItTxt = (String) CommonUtils.xpathVariable.get("gotItTxt");
        modalTitleTxt = (String) CommonUtils.xpathVariable.get("modalTitleTxt");
        closeButtonTxt = (String) CommonUtils.xpathVariable.get("closeButtonTxt");

        SEARCH_FIELD = Target.the("Search field").locatedBy(searchFieldTxt);
        DEFAULT_SEARCH_BUTTON = Target.the("Default search button").locatedBy(defaultSearchButtonTxt);
        GOT_IT_BUTTON = Target.the("Got it button").locatedBy(gotItTxt);
        MODAL_TITLE = Target.the("Modal title").locatedBy(modalTitleTxt);
        CLOSE_MODAL_BUTTON = Target.the("Close modal").locatedBy(closeButtonTxt);
    }
}
