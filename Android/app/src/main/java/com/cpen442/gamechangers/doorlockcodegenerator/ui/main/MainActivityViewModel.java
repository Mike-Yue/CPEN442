package com.cpen442.gamechangers.doorlockcodegenerator.ui.main;

import com.cpen442.gamechangers.doorlockcodegenerator.data.AuthRepository;
import com.cpen442.gamechangers.doorlockcodegenerator.data.LockRepository;
import com.cpen442.gamechangers.doorlockcodegenerator.data.Result;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.Lock;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    private AuthRepository authRepository;
    private MutableLiveData<Result<Lock>> addLockResult = new MutableLiveData<>();
    private LockRepository lockRepository;

    public MainActivityViewModel(AuthRepository authRepository, LockRepository lockRepository) {
        this.authRepository = authRepository;
        this.lockRepository = lockRepository;
    }

    public MutableLiveData<Result<Lock>> getAddLockResult() {
        return addLockResult;
    }

    public void addLock(String serial_number, String display_name) {
        Result<Lock> result = lockRepository.addLock(serial_number, display_name);
        addLockResult.setValue(result);
    }

    public List<Lock> getLocks(int timeout) throws ExecutionException, InterruptedException, TimeoutException {
        return lockRepository.getLocks().get(timeout, TimeUnit.MILLISECONDS);
    }
}
