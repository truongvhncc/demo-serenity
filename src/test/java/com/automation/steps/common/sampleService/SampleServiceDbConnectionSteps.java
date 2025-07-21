package com.automation.steps.common.sampleService;

import com.automation.steps.common.questions.OpenConnectionToDatabase;
import com.automation.steps.common.utils.CommonUtils;
import net.serenitybdd.screenplay.Actor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class SampleServiceDbConnectionSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleServiceDbConnectionSteps.class);
    public Connection sampleServiceConn;

    public void initConn(Actor actor) {
        sampleServiceConn = OpenConnectionToDatabase.withInfo(
                CommonUtils.variableProperty.get("gtpSampleServiceDatabaseUrl"),
                CommonUtils.variableProperty.get("gtpSampleServiceDatabaseUsername"),
                CommonUtils.variableProperty.get("gtpSampleServiceDatabasePassword")
        ).answeredBy(actor);
        LOGGER.info("Connect to SampleService database");
    }

    public void closeConn() throws SQLException {
        sampleServiceConn.close();
    }
}
