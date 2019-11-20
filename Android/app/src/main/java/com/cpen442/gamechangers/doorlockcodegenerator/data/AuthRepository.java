package com.cpen442.gamechangers.doorlockcodegenerator.data;


import com.cpen442.gamechangers.doorlockcodegenerator.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

public class AuthRepository {

    private static volatile AuthRepository instance;

    private AuthDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    //private constructor : singleton access
    private AuthRepository(AuthDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static AuthRepository getInstance(AuthDataSource dataSource) {
        if (instance == null) {
            instance = new AuthRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password) {
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }

    public Result<LoggedInUser> signup(String email, String password, String serial_number,
                                       String last_name, String first_name, String display_name)  {
        Result<LoggedInUser> result = dataSource.signup(email, password, serial_number, last_name,
                first_name, display_name);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }



}
