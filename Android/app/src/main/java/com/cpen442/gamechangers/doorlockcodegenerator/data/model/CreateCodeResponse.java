package com.cpen442.gamechangers.doorlockcodegenerator.data.model;

import com.google.gson.annotations.SerializedName;

public class CreateCodeResponse {

    @SerializedName("Message")
    private String message;

    public String getMessage() {
        return message;
    }
}
