package com.cpen442.gamechangers.doorlockcodegenerator.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cpen442.gamechangers.doorlockcodegenerator.R;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class AddLockDiaLogFragment extends DialogFragment {
    private MainActivityViewModel mainActivityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_addlock, container, false);

        mainActivityViewModel = ViewModelProviders.of(getActivity(),
                new MainActivityViewModelFactory()).get(MainActivityViewModel.class);

        final EditText serial_number = v.findViewById(R.id.dialog_serialNumber);
        final EditText display_name = v.findViewById(R.id.dialog_displayName);
        // Watch for button clicks.
        final Button add_button = v.findViewById(R.id.add_button);
        final Button cancel_button = v.findViewById(R.id.cancel_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityViewModel.addLock(serial_number.getText().toString(),
                        display_name.getText().toString());
                getDialog().dismiss();
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();
            }
        });
        return v;
    }
}
