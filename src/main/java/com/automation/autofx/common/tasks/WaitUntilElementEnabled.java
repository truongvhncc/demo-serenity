package com.automation.autofx.common.tasks;

import com.automation.autofx.common.utils.CommonUtils;
import com.automation.autofx.common.utils.ExceptionHandlerUtils;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaitUntilElementEnabled implements Task {
    private Target target;
    private By byLocator;
    private String targetName;
    private boolean throwException = true;
    private long timeout;
    Logger LOGGER;

    public WaitUntilElementEnabled(Target target, boolean... throwException) {
        this.LOGGER = LoggerFactory.getLogger(WaitUntilElementEnabled.class);
        this.target = target;
        this.targetName = target.getName();
        this.timeout = CommonUtils.UI_WAITING_TOTAL_TIME;
        if (throwException.length > 0 && !throwException[0]) {
            this.throwException = false;
        }
    }

    public static Performable performWithInfo(Target target, boolean... throwException) {
        return new WaitUntilElementEnabled(target, throwException);
    }

    @Step("{0} waits until element #targetName is enabled")
    public <T extends Actor> void performAs(T t) {
        WebDriverWait wait = new WebDriverWait(BrowseTheWeb.as(t).getDriver(), this.timeout / 1000L);
        long endTime = System.currentTimeMillis() + this.timeout;
        long pollingInterval = (long) CommonUtils.UI_WAITING_TIME_INTERVAL;

        try {
            while(System.currentTimeMillis() < endTime) {
                try {
                    if (this.target != null) {
                        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(this.target.getCssOrXPathSelector())));
                        t.attemptsTo(new Performable[]{Scroll.to(this.target)});
                    } else {
                        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(this.byLocator));
                        t.attemptsTo(new Performable[]{Scroll.to(new By[]{this.byLocator})});
                    }

                    return;
                } catch (Exception e) {
                    ExceptionHandlerUtils.printStackTrace(String.format("Waiting for element %s to be enabled", this.targetName), e);
                    this.LOGGER.info("Handling exception during waiting time: " + e.getMessage());
                    Thread.sleep(pollingInterval);
                    this.LOGGER.info(String.format("Retrying to wait for %s element enabled", this.targetName));
                }
            }

            throw new Exception("Timeout reached");
        } catch (Exception e) {
            Serenity.takeScreenshot();
            if (this.throwException) {
                throw new AssertionError(String.format("Element %s is not enabled after waiting for %s milliseconds. \n%s", this.targetName, this.timeout, e));
            } else {
                ExceptionHandlerUtils.printStackTrace(String.format("Waiting for element %s to be enabled after waiting for %s milliseconds", this.targetName, this.timeout), e);
            }
        }
    }
}
