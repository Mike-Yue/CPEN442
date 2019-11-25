package com.cpen442.gamechangers.doorlockcodegenerator.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cpen442.gamechangers.doorlockcodegenerator.R;
import com.cpen442.gamechangers.doorlockcodegenerator.data.Result;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.Lock;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LockListAdapter lockListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = ViewModelProviders.of(this,
                new MainActivityViewModelFactory()).get(MainActivityViewModel.class);

        FloatingActionButton addLockFab = findViewById(R.id.addLock_button);

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

        lockListAdapter = new LockListAdapter();
        recyclerView.setAdapter(lockListAdapter);

        addLockFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddLockDialog();
            }
        });

        mainActivityViewModel.getFetchLocksResult().observe(this,
                new Observer<Result<List<Lock>>>() {
            @Override
            public void onChanged(Result<List<Lock>> result) {
                if (result instanceof Result.Success) {
                    List<Lock> locks = ((Result.Success<List<Lock>>) result).getData();
                    lockListAdapter.fetchLocks(locks);
                } else if (result instanceof Result.Error) {
                    Toast.makeText(getApplicationContext(),
                            ((Result.Error) result).getError(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        mainActivityViewModel.getAddLockResult().observe(this, new Observer<Result<Lock>>() {
            @Override
            public void onChanged(Result<Lock> result) {
                if (result instanceof Result.Success) {
                    Lock newLock = ((Result.Success<Lock>) result).getData();
                    lockListAdapter.addLock(newLock);
                } else if (result instanceof Result.Error) {
                    Toast.makeText(getApplicationContext(),
                            ((Result.Error) result).getError(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showAddLockDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialogFragment = new AddLockDiaLogFragment();
        dialogFragment.show(getSupportFragmentManager(), "addLockDialog");
    }
}
