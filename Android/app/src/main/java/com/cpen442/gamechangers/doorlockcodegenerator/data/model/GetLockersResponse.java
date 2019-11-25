package com.cpen442.gamechangers.doorlockcodegenerator.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetLockersResponse {

    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private List<Lock> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Lock> getResults() {
        return results;
    }

    public void setResults(List<Lock> result) {
        this.results = result;
    }
}
