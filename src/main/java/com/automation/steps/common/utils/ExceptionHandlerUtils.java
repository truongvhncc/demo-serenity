package com.automation.steps.common.utils;

import net.serenitybdd.core.Serenity;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionHandlerUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerUtils.class);

    public ExceptionHandlerUtils() {
    }

    public static void printStackTrace(String occurEvent, Throwable t) {
        LOGGER.error("Exception is handle while [{}]" ,occurEvent, t);
        t.printStackTrace();
        try {
            Serenity.recordReportData().withTitle("Exception is handle while: [" + occurEvent + "]").andContents(ExceptionUtils.getStackTrace(t));
        } catch (Exception e) {
            if (!e.getMessage().equals("No BaseStepListener has been registered")) {
                throw new AssertionError("Error while reporting stack trace: " + e);
            }
            LOGGER.error("Exception is not shown on Serenity report");
        }
    }

    public static void printStackTrace(Throwable t) {
        printStackTrace("handling exception", t);
    }
}
