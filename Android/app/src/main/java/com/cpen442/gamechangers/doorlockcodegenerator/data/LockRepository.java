package com.cpen442.gamechangers.doorlockcodegenerator.data;

import com.cpen442.gamechangers.doorlockcodegenerator.data.model.Lock;

import java.util.ArrayList;
import java.util.List;

public class LockRepository {

    private static volatile LockRepository instance;

    private List<Lock> locks;

    private LockRepository(List<Lock> locks) {
        this.locks = locks;
    }

    public static LockRepository getInstance() {
        if (instance == null) {
            instance = new LockRepository(new ArrayList<Lock>());
        }
        return instance;
    }

    public List<Lock> getLocks() {
        return locks;
    }

    public Result<Lock> addLock(String serial_number, String display_name) {
        Lock newLock = new Lock(serial_number, display_name);
        locks.add(newLock);
        return new Result.Success<Lock>(newLock);
    }
}
