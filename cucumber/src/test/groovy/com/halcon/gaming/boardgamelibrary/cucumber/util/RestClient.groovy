package com.halcon.gaming.boardgamelibrary.cucumber.util


import groovy.json.JsonOutput

class RestClient {
    private String accessToken
    private Integer requestResponseCode
    private String requestResponseText

    private static openConnection(String path) {
        return new URL("http://localhost:8080${path}").openConnection()
    }

    private static URLConnection performGetRequest(String path, String accessToken = null) {
        URLConnection request = openConnection(path)
        request.setInstanceFollowRedirects(false)
        if (accessToken != null) {
            request.setRequestProperty("Authorization", "Bearer " + accessToken)
        }
        return request
    }

    private static URLConnection performPostRequest(String path, def body) {
        URLConnection request = openConnection(path)
        request.setRequestMethod("POST")
        request.setDoOutput(true)
        request.setRequestProperty("Content-Type", "application/json")
        request.getOutputStream().write(JsonOutput.toJson(body).getBytes("UTF-8"))
        return request
    }

    void GET(String path) {
        URLConnection request = RestClient.performGetRequest(path, accessToken)
        requestResponseCode = request.getResponseCode()
        requestResponseText = requestResponseCode == 200 ? request.getInputStream().getText() : null
    }

    def getResponseCode() {
        return requestResponseCode
    }
}
