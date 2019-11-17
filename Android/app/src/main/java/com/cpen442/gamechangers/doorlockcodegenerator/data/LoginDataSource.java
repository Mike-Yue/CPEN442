package com.cpen442.gamechangers.doorlockcodegenerator.data;


import com.cpen442.gamechangers.doorlockcodegenerator.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

public class LoginDataSource {

    public Result<LoggedInUser> login(String email, String password) {
        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(java.util.UUID.randomUUID().toString(),
                            "test@test.com",
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
