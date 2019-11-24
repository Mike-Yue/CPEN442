package com.cpen442.gamechangers.doorlockcodegenerator.httpClient;

import com.cpen442.gamechangers.doorlockcodegenerator.data.model.GetLockersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LockService {

    @GET("/locks")
    Call<GetLockersResponse> getLocks(@Header("Authorization") String token);
    // token = "Token "+"(value of Token)"

}
