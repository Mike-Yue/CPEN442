package com.cpen442.gamechangers.doorlockcodegenerator.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.cpen442.gamechangers.doorlockcodegenerator.R;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.Lock;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter lockListAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.lockList);
        recyclerView.setHasFixedSize(true);
        // Linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Add space between items
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent,
                                       @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 30;
            }
        });

        // specify an adapter
        List<Lock> locks = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Lock lock = new Lock();
            lock.setDisplay_name("test" + i);
            locks.add(lock);
        }
        lockListAdapter = new LockListAdapter(locks);
        recyclerView.setAdapter(lockListAdapter);
    }
}
