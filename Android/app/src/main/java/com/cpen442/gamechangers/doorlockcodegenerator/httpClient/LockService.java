package com.cpen442.gamechangers.doorlockcodegenerator.httpClient;

import com.cpen442.gamechangers.doorlockcodegenerator.data.model.GetLockersResponse;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.Lock;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LockService {

    @GET("/locks")
    Call<GetLockersResponse> getLocks(@Header("Authorization") String token);
    // token = "Token "+"(value of Token)"

    @POST("/locks")
    Call<Void> addLock(@Header("Authorization") String token, @Body Lock lock);



}
