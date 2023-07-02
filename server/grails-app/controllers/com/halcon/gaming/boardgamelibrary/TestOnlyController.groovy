package com.halcon.gaming.boardgamelibrary

import grails.converters.JSON

class TestOnlyController {
    static responseFormats = ['json', 'xml']

    def index() {
        render text: [] as JSON, contentType: 'application/json'
    }
}
