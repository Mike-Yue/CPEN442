package com.cpen442.gamechangers.doorlockcodegenerator.httpClient;

import com.cpen442.gamechangers.doorlockcodegenerator.data.model.CreateCodeRequest;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.CreateCodeResponse;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.GetCodesResponse;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.GetLockersResponse;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.Lock;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface LockService {

    @GET("/locks")
    Call<GetLockersResponse> getLocks(@Header("Authorization") String token);
    // token = "Token "+"(value of Token)"

    @POST("/locks/")
    Call<Void> addLock(@Header("Authorization") String token, @Body Lock lock);

    @POST("/codes/")
    Call<CreateCodeResponse> createCode(@Header("Authorization") String token,
                                        @Body CreateCodeRequest createCodeRequest);

    @GET("/codes/")
    Call<GetCodesResponse> getCodes(@Header("Authorization") String token);

    @GET
    Call<GetCodesResponse> getCodes(@Url String url, @Header("Authorization") String token);





}
