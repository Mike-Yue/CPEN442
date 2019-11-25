package com.cpen442.gamechangers.doorlockcodegenerator.data;

import com.cpen442.gamechangers.doorlockcodegenerator.data.model.Lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public enum LockRepository {
    INSTANCE(new ArrayList<Lock>());

    private static volatile LockRepository instance;

    private List<Lock> locks;

    LockRepository(List<Lock> locks) {
        this.locks = locks;
    }

//    public static LockRepository getInstance() {
//        if (instance == null) {
//            instance = new LockRepository(new ArrayList<Lock>());
//        }
//        return instance;
//    }

    public Future<List<Lock>> getLocks() {
//        return locks;
        return null;
    }

    public Result<Lock> addLock(String serial_number, String display_name) {
        Lock newLock = new Lock(serial_number, display_name);
        locks.add(newLock);
        return new Result.Success<Lock>(newLock);
    }
}
