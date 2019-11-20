package com.cpen442.gamechangers.doorlockcodegenerator.httpClient;

import com.cpen442.gamechangers.doorlockcodegenerator.data.model.LoggedInUser;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.LoginRequest;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.SignupRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthService {

    @POST("login")
    Call<LoggedInUser> login(@Body LoginRequest loginRequest);

    @POST("users")
    Call<LoggedInUser> signup(@Body SignupRequest signupRequest);

}
