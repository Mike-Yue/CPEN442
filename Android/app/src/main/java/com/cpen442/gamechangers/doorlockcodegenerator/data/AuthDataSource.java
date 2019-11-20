package com.cpen442.gamechangers.doorlockcodegenerator.data;


import com.cpen442.gamechangers.doorlockcodegenerator.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

public class AuthDataSource {

    public Result<LoggedInUser> login(String email, String password) {
        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(java.util.UUID.randomUUID().toString());
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public Result<LoggedInUser> signup(String email, String password, String serial_number,
                                       String last_name, String first_name, String display_name) {
        LoggedInUser fakeUser =
                new LoggedInUser(java.util.UUID.randomUUID().toString());
        return new Result.Success<>(fakeUser);
    }
}
