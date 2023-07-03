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
}
