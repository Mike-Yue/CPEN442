package com.cpen442.gamechangers.doorlockcodegenerator.data.model;

import com.google.gson.annotations.SerializedName;

public class CodeInfo {

    @SerializedName("created_by")
    private User created_by;

    @SerializedName("lock")
    private Lock lock;

    @SerializedName("code")
    private int code;

    @SerializedName("expiry_time")
    private String expiry_time;

    @SerializedName("expired")
    private boolean expired;

    @SerializedName("creation_time")
    private String creation_time;

    @SerializedName("used_at_time")
    private String used_at_time;

    public User getCreated_by() {
        return created_by;
    }

    public Lock getLock() {
        return lock;
    }

    public int getCode() {
        return code;
    }

    public String getExpiry_time() {
        return expiry_time;
    }

    public boolean isExpired() {
        return expired;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public String getUsed_at_time() {
        return used_at_time;
    }
}
