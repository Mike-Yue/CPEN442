package com.cpen442.gamechangers.doorlockcodegenerator.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetLockersResponse {

    @SerializedName("result")
    private List<Lock> result;
}
