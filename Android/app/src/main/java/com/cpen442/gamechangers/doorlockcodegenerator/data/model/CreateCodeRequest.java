package com.cpen442.gamechangers.doorlockcodegenerator.data.model;

import com.google.gson.annotations.SerializedName;

public class CreateCodeRequest {

    @SerializedName("lock_id")
    private String lock_id;

    @SerializedName("expiry_time")
    private String expiry_time;

    public CreateCodeRequest(String lock_id, String expiry_time) {
        this.lock_id = lock_id;
        this.expiry_time = expiry_time;
    }

    public String getLock_id() {
        return lock_id;
    }

    public String getExpiry_time() {
        return expiry_time;
    }
}
