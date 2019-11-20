package com.cpen442.gamechangers.doorlockcodegenerator.ui.auth.signup;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cpen442.gamechangers.doorlockcodegenerator.R;
import com.cpen442.gamechangers.doorlockcodegenerator.ui.main.MainActivity;
import com.cpen442.gamechangers.doorlockcodegenerator.ui.auth.AuthResult;


import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class SignupActivity extends AppCompatActivity {

    private SignupViewModel signupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupViewModel = ViewModelProviders.of(this, new SignupViewModelFactory())
                .get(SignupViewModel.class);

        final EditText emailEditText = findViewById(R.id.signup_email);
        final EditText passwordEditText = findViewById(R.id.signup_password);
        final EditText lastNameEditText = findViewById(R.id.signup_lastName);
        final EditText firstNameEditText = findViewById(R.id.signup_firstName);
        final EditText serialNumEditText = findViewById(R.id.signup_serialNumber);
        final EditText displayNameEditText = findViewById(R.id.signup_displayName);
        final Button submit_button = findViewById(R.id.signup_submit);
        final ProgressBar loadingProgressBar = findViewById(R.id.signup_loading);


        signupViewModel.getSignupFormState().observe(this, new Observer<SignupFormState>() {
            @Override
            public void onChanged(SignupFormState signupFormState) {
                if (signupFormState == null) {
                    return;
                }
                submit_button.setEnabled(signupFormState.isDataValid());
                if (signupFormState.getEmailError() != null) {
                    emailEditText.setError(getString(signupFormState.getEmailError()));
                }
                if (signupFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(signupFormState.getPasswordError()));
                }
            }
        });

        signupViewModel.getAuthResult().observe(this, new Observer<AuthResult>() {
            @Override
            public void onChanged(AuthResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.isSuccess()) {
                    loginSucceeded();
                }
                setResult(Activity.RESULT_OK);
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                signupViewModel.signUpDataChanged(
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                signupViewModel.signup(
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        serialNumEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        firstNameEditText.getText().toString(),
                        displayNameEditText.getText().toString());
            }
        });

    }

    private void loginSucceeded() {
        String welcome = "Welcome !";
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
