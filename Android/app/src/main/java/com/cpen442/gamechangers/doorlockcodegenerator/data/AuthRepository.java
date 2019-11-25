package com.cpen442.gamechangers.doorlockcodegenerator.data;


import com.cpen442.gamechangers.doorlockcodegenerator.data.model.LoggedInUser;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.LoginRequest;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.SignupRequest;
import com.cpen442.gamechangers.doorlockcodegenerator.httpClient.AuthService;
import com.cpen442.gamechangers.doorlockcodegenerator.httpClient.RetrofitClient;

import java.io.IOException;

import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http1.Http1Codec;
import retrofit2.Response;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

public class AuthRepository {

    private static volatile AuthRepository instance;

    private AuthService authService;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    //private constructor : singleton access
    private AuthRepository() {
        authService = RetrofitClient.getRetrofitInstance().create(AuthService.class);
    }

    public static AuthRepository getInstance() {
        if (instance == null) {
            instance = new AuthRepository();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
    }

    public String getToken() {
        return user.getAuth_token();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String email, String password) {
        try {
            Response<LoggedInUser> response = authService.login(new LoginRequest(email, password))
                    .execute();
            if (response.code() != 200) {
                return new Result.Error("Cannot create new users.");
            } else {
                LoggedInUser user = response.body();
                setLoggedInUser(user);
                return new Result.Success<LoggedInUser>(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result.Error(e.getMessage());
        }
    }

    public Result signup(String email, String password)  {
        try {
            Response response = authService.signup(new SignupRequest(email, password))
                    .execute();
            if (response.code() != 201) {
                return new Result.Error("Cannot create new users.");
            } else {
                return new Result.Success<>(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result.Error(e.getMessage());
        }
    }

}
