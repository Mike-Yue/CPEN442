package com.cpen442.gamechangers.doorlockcodegenerator.ui.main;

import com.cpen442.gamechangers.doorlockcodegenerator.data.AuthRepository;
import com.cpen442.gamechangers.doorlockcodegenerator.data.LockRepository;
import com.cpen442.gamechangers.doorlockcodegenerator.data.Result;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.CodeInfo;
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
    private MutableLiveData<Result<List<Lock>>> fetchLocksResult = new MutableLiveData<>();
    private MutableLiveData<Result<String>> createCodeResult = new MutableLiveData<>();
    private MutableLiveData<Result<CodeInfo>> fetchCodeResult = new MutableLiveData<>();
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

    public void createCode(String lock_id, String expiry_time) {
        new Thread(new CreateCodeTask(lock_id, expiry_time)).start();
    }

    public MutableLiveData<Result<String>> getCreateCodeResult() {
        return createCodeResult;
    }

    public void getCodeInfo(String lock_id) {
        new Thread(new FetchCodeTask(lock_id)).start();
    }

    public MutableLiveData<Result<CodeInfo>> getFetchCodeResult() {
        return fetchCodeResult;
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
            String token = "Token " + authRepository.getToken();
            Result<Lock> result = lockRepository.addLock(token, serial_number, display_name);
            addLockResult.postValue(result);
        }
    }

    private class FetchLocksTask implements Runnable {
        @Override
        public void run() {
            String token = "Token " + authRepository.getToken();
            Result<List<Lock>> result = lockRepository.getLocks(token);
            fetchLocksResult.postValue(result);
        }
    }

    private class FetchCodeTask implements Runnable {
        private String lock_id;

        public FetchCodeTask(String lock_id) {
            this.lock_id = lock_id;
        }

        @Override
        public void run() {
            String token = "Token " + authRepository.getToken();
            Result<CodeInfo> result = lockRepository.getCodeInfo(token, lock_id);
            fetchCodeResult.postValue(result);
        }
    }

    private class CreateCodeTask implements Runnable {
        private String lock_id;
        private String expiry_time;

        public CreateCodeTask(String lock_id, String expiry_time) {
            this.lock_id = lock_id;
            this.expiry_time = expiry_time;
        }

        @Override
        public void run() {
            String token = "Token " + authRepository.getToken();
            Result<String> result = lockRepository.createCode(token, lock_id, expiry_time);
            createCodeResult.postValue(result);
        }
    }
}
