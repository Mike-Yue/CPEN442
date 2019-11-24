package com.cpen442.gamechangers.doorlockcodegenerator.ui.auth.login;

import android.util.Patterns;

import com.cpen442.gamechangers.doorlockcodegenerator.R;
import com.cpen442.gamechangers.doorlockcodegenerator.data.AuthRepository;
import com.cpen442.gamechangers.doorlockcodegenerator.data.Result;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.LoggedInUser;
import com.cpen442.gamechangers.doorlockcodegenerator.ui.auth.AuthResult;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<AuthResult> authResult = new MutableLiveData<>();
    private AuthRepository authRepository;

    LoginViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public MutableLiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public MutableLiveData<AuthResult> getAuthResult() {
        return authResult;
    }

    public void login(String email, String password) {
        // can be launched in a separate asynchronous job
        new Thread(new LoginTask(email, password)).start();
    }

    public void loginDataChanged(String email, String password) {
        if (!isEmailValid(email)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_email, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private class LoginTask implements Runnable {
        private String email;
        private String password;

        public LoginTask(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        public void run() {
            Result<LoggedInUser> result = authRepository.login(email, password);
            if (result instanceof Result.Success) {
                LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                authResult.postValue(new AuthResult(true));
            } else {
                authResult.postValue(new AuthResult(R.string.login_failed));
            }
        }
    }
}
