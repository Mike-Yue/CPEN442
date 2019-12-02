package com.cpen442.gamechangers.doorlockcodegenerator.data.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("email")
    private String email;

    public String getEmail() {
        return email;
    }
}
