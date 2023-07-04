package com.halcon.gaming.boardgamelibrary.cucumber

import com.halcon.gaming.boardgamelibrary.cucumber.util.ChromeDriverManager
import com.halcon.gaming.boardgamelibrary.cucumber.util.UserRepository
import groovy.json.JsonSlurper
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.awaitility.core.ConditionTimeoutException
import org.openqa.selenium.By
import org.openqa.selenium.SessionNotCreatedException
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.html5.LocalStorage
import org.openqa.selenium.html5.WebStorage

import java.nio.file.Paths
import java.util.concurrent.TimeUnit

import static org.awaitility.Awaitility.await
import static org.junit.jupiter.api.Assertions.assertEquals

class WebStepDefinitions {
    WebDriver webDriver = null

    private final ChromeDriverManager chromeDriverManager = new ChromeDriverManager(Paths.get("build", "chromedriver"))
    private final UserRepository userRepository

    WebStepDefinitions(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    @Before
    void startWebDriver() {
        chromeDriverManager.installChromeDriver()

        ChromeOptions options = new ChromeOptions()
        options.addArguments("--headless")
        options.addArguments("--no-sandbox")
        options.addArguments("--remote-allow-origins=*")
        while (webDriver == null) {
            try {
                webDriver = new ChromeDriver(options)
            } catch (SessionNotCreatedException e) {
                Thread.sleep(5000)
            }
        }
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        webDriver.manage().window().maximize()
    }

    @After
    void stopWebDriver() {
        webDriver.quit()
    }

    @Given("we have directed the browser to {string}")
    @When("we direct the browser to {string}")
    void we_direct_the_browser_to(String path) {
        def success = false
        def url = "http://localhost:8080${path}"
        3.times {
            if (!success) {
                try {
                    await().atMost(10, TimeUnit.SECONDS).until {
                        if (webDriver.currentUrl != url) {
                            webDriver.get(url)
                            return false
                        } else {
                            return true
                        }
                    }
                    success = true
                } catch (ConditionTimeoutException e) {
                }
            }
        }
        assert success
    }

    @Given("we are logged in as {string}")
    void we_are_logged_in_as(String username) {
        we_direct_the_browser_to("/#/")
        we_click_on_the_component_with_id("open_login")
        we_enter_the_value_into_the_element_with_id(username, "username")
        we_enter_the_value_into_the_element_with_id(userRepository.userPassword(username), "password")
        we_click_on_the_component_with_id("submit_login")
    }

    @When("we click on the component with id {string}")
    void we_click_on_the_component_with_id(String id) {
        await().atMost(10, TimeUnit.SECONDS).until {
            try {
                def component = webDriver.findElement(By.xpath("//*[@id='${id}']"))
                component.click()
                return true
            } catch (StaleElementReferenceException e) {
                return false
            }
        }
    }

    @When("we enter the value {string} into the component with id {string}")
    void we_enter_the_value_into_the_element_with_id(String value, String id) {
        for (; ;) {
            try {
                def element = webDriver.findElement(By.id(id))
                element.clear()
                element.sendKeys(value)
                return
            } catch (StaleElementReferenceException e) {
            }
        }
    }

    @When("we enter the value {string} into the autocomplete component with id {string}")
    void we_enter_the_value_into_the_autocomplete_component_with_id(String value, String id) {
        await().atMost(10, TimeUnit.SECONDS).until {
            try {
                def component = webDriver.findElement(By.xpath("//div[@id='" + id + "']//input"))
                component.clear()
                component.sendKeys(value)
                return true
            } catch (StaleElementReferenceException e) {
                return false
            }
        }
    }

    def autocompleteListItems(String autocompleteId) {
        def listId = webDriver.findElement(By.xpath("//div[@id='${autocompleteId}']//div[@role='combobox']")).getAttribute('aria-owns')
        return webDriver.findElements(By.xpath("//div[@id='${listId}']/div"))
    }

    @When("we click on the {string} option in the autocomplete component with id {string}")
    void we_click_on_the_option_in_the_autocomplete_component_with_id(String value, String id) {
        await().atMost(10, TimeUnit.SECONDS).until {
            try {
                for (def item : autocompleteListItems(id)) {
                    if (item.getText() == value) {
                        item.click()
                        return true
                    }
                }
                return false
            } catch (StaleElementReferenceException e) {
                return false
            }
        }
    }

    @When("we scan the qrcode {string} into the qrcode reader component with id {string}")
    void we_scan_the_qrcode_into_the_qrcode_reader_component_with_id(String filename, id) {
        webDriver.findElement(By.xpath("//div[@id='${id}']//input[@type='file']")).sendKeys(new File("src/test/resources/qrcodes/${filename}").absolutePath)
    }

    @Then("we will be logged in as {string}")
    void we_will_be_logged_in_as(String username) {
        LocalStorage localStorage
        await().atMost(5, TimeUnit.SECONDS).until {
            localStorage = ((WebStorage) webDriver).getLocalStorage()
            localStorage.getItem("auth") != null
        }
        assertEquals(username, new JsonSlurper().parseText(localStorage.getItem("auth")).username)
    }

    @Then("we will not be logged in")
    void we_will_not_be_logged_in() {
        await().atMost(5, TimeUnit.SECONDS).until {
            LocalStorage localStorage = ((WebStorage) webDriver).getLocalStorage()
            localStorage.getItem("auth") == null
        }
    }

    @Then("the current URL will be {string}")
    void the_current_url_will_be(String url) {
        await().atMost(5, TimeUnit.SECONDS).until {
            webDriver.currentUrl == "http://localhost:8080${url}"
        }
    }

    @Then("we will see a component with id {string}")
    void we_will_see_a_component_with_id(String id) {
        await().atMost(5, TimeUnit.SECONDS).until {
            webDriver.findElements(By.id(id)).size() == 1
        }
    }

    @Then("we will not see a component with id {string}")
    void we_will_not_see_an_element_with_id(String id) {
        await().atMost(1, TimeUnit.MINUTES).until {
            webDriver.findElements(By.id(id)).empty
        }
    }


    @Then("we will see {string} in the dropdown list of the autocomplete component with id {string}")
    void we_will_see_in_the_dropdown_list_of_the_autocomplete_component_with_id(String value, String id) {
        await().atMost(5, TimeUnit.SECONDS).until {
            for (def item : autocompleteListItems(id)) {
                if (item.getText() == value) {
                    return true
                }
            }
            return false
        }
    }

    @Then("the text-field with id {string} will contain {string}")
    void the_text_field_with_id_will_contain(String id, String value) {
        sleep(500)
        assertEquals(value, webDriver.findElement(By.id(id)).getAttribute("value"))
    }

    @Then("the button with id {string} will be labelled {string}")
    void the_button_with_id_will_be_labelled(String id, String label) {
        await().atMost(5, TimeUnit.SECONDS).until {
            webDriver.findElement(By.xpath("//*[@id=\"${id}\"]/span")).text.equalsIgnoreCase(label)
        }
    }
}
