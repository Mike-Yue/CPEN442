package com.cpen442.gamechangers.doorlockcodegenerator.ui.main;

import com.cpen442.gamechangers.doorlockcodegenerator.data.AuthRepository;
import com.cpen442.gamechangers.doorlockcodegenerator.data.LockRepository;
import com.cpen442.gamechangers.doorlockcodegenerator.data.Result;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.Lock;


import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    private AuthRepository authRepository;
    private MutableLiveData<Result<Lock>> addLockResult = new MutableLiveData<>();
    private MutableLiveData<Result<List<Lock>>> fetchLocksResult = new MutableLiveData<>();
    private LockRepository lockRepository;

    public MutableLiveData<Result<List<Lock>>> getFetchLocksResult() {
        return fetchLocksResult;
    }

    public MainActivityViewModel(AuthRepository authRepository, LockRepository lockRepository) {
        this.authRepository = authRepository;
        this.lockRepository = lockRepository;
    }

    public MutableLiveData<Result<Lock>> getAddLockResult() {
        return addLockResult;
    }

    public void addLock(String serial_number, String display_name) {
        new Thread(new AddLockTask(serial_number, display_name)).start();
    }

    public void getLocks() {
        new Thread(new FetchLocksTask()).start();
    }

    private class AddLockTask implements Runnable {
        private String serial_number;
        private String display_name;

        public AddLockTask(String serial_number, String display_name) {
            this.serial_number = serial_number;
            this.display_name = display_name;
        }

        @Override
        public void run() {
            Result<Lock> result = lockRepository.addLock(authRepository.getToken(), serial_number, display_name);
            addLockResult.postValue(result);
        }
    }

    private class FetchLocksTask implements Runnable {
        @Override
        public void run() {
            Result<List<Lock>> result = lockRepository.getLocks(authRepository.getToken());
            fetchLocksResult.postValue(result);
        }
    }
}
