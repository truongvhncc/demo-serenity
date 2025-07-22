package com.automation.autofx.common.questions;

import com.automation.autofx.common.utils.DatabaseUtils;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;

import java.sql.Connection;

@Subject("Database connection opened with username #username and password #password")
public class OpenConnectionToDatabase implements Question<Connection> {
    private String url;
    private String username;
    private String password;

    public OpenConnectionToDatabase(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection answeredBy(Actor actor) {
        return DatabaseUtils.openConnectionToADatabase(this.url, this.username, this.password);
    }

    public static OpenConnectionToDatabase withInfo(String url, String username, String password) {
        return new OpenConnectionToDatabase(url, username, password);
    }
}
