package com.halcon.gaming.boardgamelibrary.cucumber.util

class UserRepository {
    private Map<String, String> userIdMap = [:]
    private Map<String, String> userPasswordMap = [:]

    void registerUser(String id, String username, String password) {
        userIdMap[username] = id
        userPasswordMap[username] = password
    }

    String userId(username) {
        return userIdMap[username]
    }

    String userPassword(username) {
        return userPasswordMap[username]
    }
}
