package com.cpen442.gamechangers.doorlockcodegenerator.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cpen442.gamechangers.doorlockcodegenerator.R;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.Lock;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LockListAdapter extends RecyclerView.Adapter<LockListAdapter.LockItemViewHolder> {
    private List<Lock> locks;


    public static class LockItemViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView displayNameView;
        public LockItemViewHolder(View v) {
            super(v);
            view= v;
            displayNameView = v.findViewById(R.id.display_name);
        }
    }

    public LockListAdapter(List<Lock> locks) {
        this.locks = locks;
    }

    @Override
    public LockListAdapter.LockItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create the view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lockview, parent, false);

        LockItemViewHolder lockItemViewHolder = new LockItemViewHolder(v);
        return lockItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LockItemViewHolder holder, int position) {
        holder.displayNameView.setText(locks.get(position).getDisplay_name());
    }

    @Override
    public int getItemCount() {
        return locks.size();
    }


}
