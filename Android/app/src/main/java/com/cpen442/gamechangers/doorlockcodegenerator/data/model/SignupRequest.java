package com.cpen442.gamechangers.doorlockcodegenerator.data.model;

import com.google.gson.annotations.SerializedName;

public class SignupRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("username")
    private String username;

    public SignupRequest(String email, String password) {
        this.email = email;
        this.password = password;
        this.username = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
