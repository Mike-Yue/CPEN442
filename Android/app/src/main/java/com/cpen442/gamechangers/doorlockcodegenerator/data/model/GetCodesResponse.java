package com.cpen442.gamechangers.doorlockcodegenerator.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCodesResponse {

    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private List<CodeInfo> codeInfos;

    @SerializedName("next")
    private String next;



    public String getNext() {
        return next;
    }

    public int getCount() {
        return count;
    }

    public List<CodeInfo> getCodeInfos() {
        return codeInfos;
    }
}
