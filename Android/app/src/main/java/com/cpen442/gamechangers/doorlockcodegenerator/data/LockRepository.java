package com.cpen442.gamechangers.doorlockcodegenerator.data;

import com.cpen442.gamechangers.doorlockcodegenerator.data.model.CreateCodeRequest;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.CreateCodeResponse;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.GetLockersResponse;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.Lock;
import com.cpen442.gamechangers.doorlockcodegenerator.httpClient.LockService;
import com.cpen442.gamechangers.doorlockcodegenerator.httpClient.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class LockRepository {

    private LockService lockService;

    private static volatile LockRepository instance;

    private List<Lock> locks;

    private LockRepository(List<Lock> locks) {
        this.locks = locks;
        lockService = RetrofitClient.getRetrofitInstance().create(LockService.class);
    }

    public static LockRepository getInstance() {
        if (instance == null) {
            instance = new LockRepository(new ArrayList<Lock>());
        }
        return instance;
    }

    public Result<List<Lock>> getLocks(String token) {
        try {
            Response<GetLockersResponse> response = lockService.getLocks(token).execute();
            if (response.code() == 200) {
                locks = response.body().getResults();
                return new Result.Success<>(locks);
            } else {
                return new Result.Error("Cannot fetch the lock list");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result.Error("Cannot fetch the lock list");
        }
    }

    public Result<Lock> addLock(String token, String serial_number, String display_name) {
        Lock newLock = new Lock(serial_number, display_name);
        try {
            Response<Void> response = lockService.addLock(token, newLock).execute();
            if (response.code() == 200) {
                locks.add(newLock);
                return new Result.Success<>(newLock);
            } else {
                return new Result.Error("Failed to create the lock");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result.Error("Failed to create the lock");
        }
    }

    public Result<String> createCode(String token, String lock_id, String expiry_time) {
        try {
            Response<CreateCodeResponse> response = lockService.createCode(token,
                    new CreateCodeRequest(lock_id, expiry_time)).execute();
            if (response.code() == 200) {
                return new Result.Success<String>(response.body().getMessage());
            } else {
                return new Result.Error("Failed to create the code");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result.Error("Failed to create the code");
        }
    }
}
