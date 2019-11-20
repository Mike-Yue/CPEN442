package com.cpen442.gamechangers.doorlockcodegenerator.data.model;


import com.google.gson.annotations.SerializedName;

/**
 * Data class that captures user information for logged in users retrieved from AuthRepository
 */
public class LoggedInUser {
    @SerializedName("token")
    private String auth_token;

    public LoggedInUser(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }
}
