package com.cpen442.gamechangers.doorlockcodegenerator.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetLockersResponse {

    @SerializedName("count")
    private int count;

    @SerializedName("result")
    private List<Lock> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Lock> getResult() {
        return result;
    }

    public void setResult(List<Lock> result) {
        this.result = result;
    }
}
