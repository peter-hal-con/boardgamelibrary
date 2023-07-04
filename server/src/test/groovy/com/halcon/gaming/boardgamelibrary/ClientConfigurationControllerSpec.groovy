package com.halcon.gaming.boardgamelibrary

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class ClientConfigurationControllerSpec extends Specification implements ControllerUnitTest<ClientConfigurationController> {

    def setup() {
    }

    def cleanup() {
    }

    void "test index"() {
        when:
        controller.index()

        then:
        status == 200
        response.json.BGG_BASE_URL == "https://boardgamegeek.com"
    }
}
