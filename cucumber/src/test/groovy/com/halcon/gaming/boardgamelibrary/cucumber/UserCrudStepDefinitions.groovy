package com.halcon.gaming.boardgamelibrary.cucumber

import com.halcon.gaming.boardgamelibrary.cucumber.util.RestClient
import io.cucumber.java.en.Then

import java.util.concurrent.TimeUnit

import static org.awaitility.Awaitility.await

class UserCrudStepDefinitions {
    private final Map<String, String> authorityIdMap = ["ROLE_ADMIN": "1", "ROLE_COMMITTEE": "2"]

    private final RestClient restClient

    UserCrudStepDefinitions(RestClient restClient) {
        this.restClient = restClient
    }

    @Then("the user with username {string} will have the password {string}")
    void the_user_with_username_will_have_the_password(String username, String password) {
        await().atMost(5, TimeUnit.SECONDS).until {
            RestClient.performAuthentication(username, password) != null
        }
    }
}
