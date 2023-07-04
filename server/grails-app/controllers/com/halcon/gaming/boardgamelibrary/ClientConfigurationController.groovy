package com.halcon.gaming.boardgamelibrary

import grails.converters.JSON

class ClientConfigurationController {
    static responseFormats = ['json', 'xml']

    def index() {
        render Collections.singletonMap("BGG_BASE_URL", System.getenv("BGG_BASE_URL") ?: "https://boardgamegeek.com") as JSON
    }
}
