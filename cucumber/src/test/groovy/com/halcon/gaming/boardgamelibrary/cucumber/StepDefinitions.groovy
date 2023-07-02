package com.halcon.gaming.boardgamelibrary.cucumber

import com.halcon.gaming.boardgamelibrary.cucumber.util.RestClient
import com.halcon.gaming.boardgamelibrary.cucumber.util.UserRepository
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

import static org.junit.jupiter.api.Assertions.*

class StepDefinitions {
    private final RestClient restClient
    private final UserRepository userRepository

    StepDefinitions(RestClient restClient, UserRepository userRepository) {
        this.restClient = restClient
        this.userRepository = userRepository
    }

    @Given("the following users exist:")
    void the_following_users_exist(io.cucumber.datatable.DataTable dataTable) {
        dataTable.asMaps().each {
            def authorities = it.authorities != null ? it.authorities.split(',') : []
            def request = RestClient.performPostRequest("/testOnly/createUser", [username: it.username, password: it.password, authorities: authorities])
            assertEquals(200, request.getResponseCode())
            userRepository.registerUser(RestClient.jsonPathParse(request.getInputStream().getText(), '$.id').toString(), it.username, it.password)
        }
    }

    @When("we perform a GET request on {string}")
    void we_perform_a_get_request_on(String path) {
        restClient.GET(path)
    }

    @When("we authenticate as {string} with password {string}")
    void we_authenticate_as_with_password(String username, String password) {
        restClient.authenticate(username, password)
    }

    @Then("we get a {int} response")
    void we_get_a_response(Integer expectedResponseCode) {
        assertEquals(expectedResponseCode, restClient.responseCode)
    }

    @Then("we will have an access token")
    void we_will_have_an_access_token() {
        assertTrue(restClient.hasAccessToken())
    }

    @Then("we will not have an access token")
    void we_will_not_have_an_access_token() {
        assertFalse(restClient.hasAccessToken())
    }
}
