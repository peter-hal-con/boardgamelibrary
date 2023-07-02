package com.halcon.gaming.boardgamelibrary.cucumber

import com.halcon.gaming.boardgamelibrary.cucumber.util.RestClient
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

import static org.junit.jupiter.api.Assertions.assertEquals

class StepDefinitions {
    private final RestClient restClient

    StepDefinitions(RestClient restClient) {
        this.restClient = restClient
    }

    @When("we perform a GET request on {string}")
    void we_perform_a_get_request_on(String path) {
        restClient.GET(path)
    }

    @Then("we get a {int} response")
    void we_get_a_response(Integer expectedResponseCode) {
        assertEquals(expectedResponseCode, restClient.responseCode)
    }
}
