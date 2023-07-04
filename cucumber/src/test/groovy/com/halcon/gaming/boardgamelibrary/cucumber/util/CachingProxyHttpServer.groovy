package com.halcon.gaming.boardgamelibrary.cucumber.util

import com.sun.net.httpserver.HttpServer
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class CachingProxyHttpServer implements AutoCloseable {
    private class CachedResponse {
        final String contentType
        final int statusCode
        final String body

        CachedResponse(String contentType, int statusCode, String body) {
            this.contentType = contentType
            this.statusCode = statusCode
            this.body = body
        }
    }

    private final HttpServer server
    private final Map<String, CachingProxyHttpServer.CachedResponse> cachedResponses = [:]
    private int cacheMissCount = 0

    CachingProxyHttpServer(String remoteTarget, int localPort = 8080) {
        server = HttpServer.create(new InetSocketAddress(localPort), 0)
        server.with {
            createContext("/") { http ->
                String requestPath = http.getRequestURI().toString()
                if (!cachedResponses.containsKey(requestPath)) {
                    URLConnection proxyRequest = new URL("${remoteTarget}${requestPath}").openConnection()
                    cachedResponses[requestPath] = new CachingProxyHttpServer.CachedResponse(proxyRequest.getHeaderField("Content-type"), proxyRequest.getResponseCode(), proxyRequest.getInputStream().getText())
                    cacheMissCount++
                }

                def response = cachedResponses[requestPath]
                http.responseHeaders.add("Content-type", response.contentType)
                http.responseHeaders.add("Access-Control-Allow-Origin", "*")
                http.sendResponseHeaders(response.statusCode, 0)
                http.responseBody.withWriter { out ->
                    out << response.body
                }
            }
            start()
        }
    }

    @Override
    void close() throws Exception {
        server.stop(0)
    }

    def getAddress() {
        return server.getAddress()
    }

    def getCacheMissCount() {
        return cacheMissCount
    }

    String exportCachedResponses() {
        return JsonOutput.toJson(cachedResponses)
    }

    def importCachedResponses(String cachedResponsesJson) {
        this.cachedResponses.putAll(new JsonSlurper().parseText(cachedResponsesJson))
        return this
    }
}
