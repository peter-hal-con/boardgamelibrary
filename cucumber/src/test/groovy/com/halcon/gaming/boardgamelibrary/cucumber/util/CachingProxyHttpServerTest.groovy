package com.halcon.gaming.boardgamelibrary.cucumber.util


import groovy.test.GroovyTestCase

class CachingProxyHttpServerTest extends GroovyTestCase {

    void testProxyHttpServer() {
        try (def server = new CachingProxyHttpServer("https://example.com")) {
            3.times {
                def request = new URL("http://localhost:${server.getAddress().port}/").openConnection()
                assertEquals(200, request.getResponseCode())
                assertTrue(request.getInputStream().getText().startsWith("<!doctype html>"))
            }

            assertEquals(1, server.getCacheMissCount())
        }
    }

    void testProxyHttpServerCachedResponses() {
        def cachedResponses
        try (def server = new CachingProxyHttpServer("https://example.com")) {
            def request = new URL("http://localhost:${server.getAddress().port}/").openConnection()
            assertEquals(200, request.getResponseCode())
            assertTrue(request.getInputStream().getText().startsWith("<!doctype html>"))
            cachedResponses = server.getCachedResponses()
        }

        try (def server = new CachingProxyHttpServer("https://example.com").loadCachedResponses(cachedResponses)) {
            3.times {
                def request = new URL("http://localhost:${server.getAddress().port}/").openConnection()
                assertEquals(200, request.getResponseCode())
                assertTrue(request.getInputStream().getText().startsWith("<!doctype html>"))
            }

            assertEquals(0, server.getCacheMissCount())
        }
    }

    void testProxyHttpServerWithBoardGameGeek() {
        try (def server = new CachingProxyHttpServer("https://boardgamegeek.com")) {
            def request = new URL("http://localhost:${server.getAddress().port}/xmlapi2/search?type=boardgame,boardgameaccessory,boardgameexpansion&query=crossbows").openConnection()
            assertEquals(200, request.getResponseCode())
            def text = request.getInputStream().getText()
            assertTrue(text.startsWith("<?xml version=\"1.0\" encoding=\"utf-8\"?>"))
            assertTrue(text.size() > 256)
        }
    }
}
