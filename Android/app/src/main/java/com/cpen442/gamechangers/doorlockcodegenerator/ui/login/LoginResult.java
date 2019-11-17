package com.cpen442.gamechangers.doorlockcodegenerator.ui.login;


import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private boolean success;
    @Nullable
    private Integer error;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable boolean success) {
        this.success =success;
    }

    public boolean isSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
