package com.automation.autofx.common.tasks;

import com.automation.autofx.common.utils.CommonUtils;
import com.automation.autofx.common.utils.ExceptionHandlerUtils;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.function.BiPredicate;

public class WaitUntilElementValue {
    static Logger LOGGER = LoggerFactory.getLogger(WaitUntilElementValue.class);

    public WaitUntilElementValue() {
    }

    public static Performable isEmpty(Target target, boolean... throwException) {
        return isEqual(target, "", throwException);
    }

    public static Performable isEqual(Target target, String expectedValue, boolean... throwException) {
        String title = String.format("{0} waits for the value of element %s is %s", target.getName(), expectedValue);
        if (Objects.equals(expectedValue, "")) {
            title = String.format("{0} waits for the value of element %s to be empty", target.getName());
        }

        String finalTitle = title;
        return Task.where(title, (actor) -> {
            WebDriverWait wait = new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), (long)(CommonUtils.UI_WAITING_TOTAL_TIME / 1000));
            long endTime = System.currentTimeMillis() + (long)CommonUtils.UI_WAITING_TOTAL_TIME;

            while(System.currentTimeMillis() < endTime) {
                try {
                    wait.until(ExpectedConditions.textToBePresentInElementValue(target.resolveFor(actor), expectedValue));
                    break;
                } catch (StaleElementReferenceException e) {
                    LOGGER.info("StaleElementReferenceException occurs: " + e.getMessage() + "retrying getting element by xpath");
                    if (System.currentTimeMillis() >= endTime) {
                        Serenity.takeScreenshot();
                        if (throwException.length > 0 && throwException[0]) {
                            throw new AssertionError(finalTitle + " for " + CommonUtils.UI_WAITING_TOTAL_TIME + " milliseconds. \n" + e);
                        }

                        ExceptionHandlerUtils.printStackTrace(finalTitle + " for " + CommonUtils.UI_WAITING_TOTAL_TIME + " milliseconds", e);
                    }
                } catch (Exception e) {
                    Serenity.takeScreenshot();
                    if (throwException.length > 0 && throwException[0]) {
                        throw new AssertionError(finalTitle + " for " + CommonUtils.UI_WAITING_TOTAL_TIME + " milliseconds. \n" + e);
                    }

                    ExceptionHandlerUtils.printStackTrace(finalTitle + " for " + CommonUtils.UI_WAITING_TOTAL_TIME + " milliseconds", e);
                }
            }

        });
    }

    public static <T, U> Performable isAsExpected(Question<T> question, U expectedValue, BiPredicate<T, U> comparison, boolean... throwException) {
        String title = String.format("{0} waits for the value of Question: %s to be %s", question.getSubject(), expectedValue);
        return Task.where(title, (actor) -> {
            long currentTime = System.currentTimeMillis();
            long timeout = System.currentTimeMillis() + (long)CommonUtils.UI_WAITING_TOTAL_TIME;
            long pollingInterval = (long)CommonUtils.UI_WAITING_TIME_INTERVAL;

            try {
                while(System.currentTimeMillis() < timeout) {
                    try {
                        if (comparison.test(question.answeredBy(actor), expectedValue)) {
                            LOGGER.info("Value is as expected for Question: [" + question.getSubject() + "] after " + (System.currentTimeMillis() - currentTime) + " ms");
                            return;
                        }
                    } catch (Exception e) {
                        ExceptionHandlerUtils.printStackTrace(title, e);
                        LOGGER.info("Handling exception during waiting time: " + e.getMessage());
                    }

                    Thread.sleep(pollingInterval);
                    LOGGER.info("Retrying to get the value of the Question: " + question.getSubject());
                }

                throw new Exception("Timeout reached");
            } catch (Exception e) {
                Serenity.takeScreenshot();
                if (throwException.length > 0 && throwException[0]) {
                    throw new AssertionError(title + " for " + timeout + " milliseconds. \n" + e);
                } else {
                    ExceptionHandlerUtils.printStackTrace(title + " for " + timeout + " milliseconds", e);
                }
            }
        });
    }
}

