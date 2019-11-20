package com.cpen442.gamechangers.doorlockcodegenerator.ui.auth;


import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
public class AuthResult {
    @Nullable
    private boolean success;
    @Nullable
    private Integer error;

    public AuthResult(@Nullable Integer error) {
        this.error = error;
    }

    public AuthResult(@Nullable boolean success) {
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
