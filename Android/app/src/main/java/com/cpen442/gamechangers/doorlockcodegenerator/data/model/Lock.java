package com.cpen442.gamechangers.doorlockcodegenerator.data.model;

public class Lock {
    private String id;
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

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}
