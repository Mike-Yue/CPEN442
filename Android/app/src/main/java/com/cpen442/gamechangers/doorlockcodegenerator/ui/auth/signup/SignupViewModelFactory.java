package com.cpen442.gamechangers.doorlockcodegenerator.ui.auth.signup;

import com.cpen442.gamechangers.doorlockcodegenerator.data.AuthRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


/**
 * ViewModel provider factory to instantiate SignupViewModel.
 * Required given SignupViewModel has a non-empty constructor
 */
class SignupViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SignupViewModel.class)) {
            return (T) new SignupViewModel(AuthRepository.getInstance());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
