package com.cpen442.gamechangers.doorlockcodegenerator.data.model;

import com.google.gson.annotations.SerializedName;

public class Lock {
    @SerializedName("lock_id")
    private String id;

    @SerializedName("display_name")
    private String display_name;

    public Lock(String id, String display_name) {
        this.id = id;
        this.display_name = display_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    @Override
    public String toString() {
        return !display_name.equals("") ? display_name : id ;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}
