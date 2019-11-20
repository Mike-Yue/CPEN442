package com.cpen442.gamechangers.doorlockcodegenerator.ui.auth.signup;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cpen442.gamechangers.doorlockcodegenerator.R;
import com.cpen442.gamechangers.doorlockcodegenerator.data.AuthRepository;
import com.cpen442.gamechangers.doorlockcodegenerator.data.Result;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.LoggedInUser;
import com.cpen442.gamechangers.doorlockcodegenerator.ui.auth.AuthResult;

class SignupViewModel extends ViewModel {

    private MutableLiveData<SignupFormState> signupFormState = new MutableLiveData<>();
    private MutableLiveData<AuthResult> authResult = new MutableLiveData<>();
    private AuthRepository authRepository;

    SignupViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public MutableLiveData<SignupFormState> getSignupFormState() {
        return signupFormState;
    }

    public MutableLiveData<AuthResult> getAuthResult() {
        return authResult;
    }

    public void signup(String email, String password, String serial_number, String first_name,
                       String last_name, String display_name) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = authRepository.signup(email, password, serial_number,
                first_name, last_name, display_name);
        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            authResult.setValue(new AuthResult(true));
        } else {
            authResult.setValue(new AuthResult(R.string.login_failed));
        }
    }

    public void signUpDataChanged(String email, String password) {
        if (!isEmailValid(email)) {
            signupFormState.setValue(new SignupFormState(R.string.invalid_email, null));
        } else if (!isPasswordValid(password)) {
            signupFormState.setValue(new SignupFormState(null, R.string.invalid_password));
        } else {
            signupFormState.setValue(new SignupFormState(true));
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
}
