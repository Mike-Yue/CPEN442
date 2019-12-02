package com.cpen442.gamechangers.doorlockcodegenerator.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cpen442.gamechangers.doorlockcodegenerator.R;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class DateTimePickerFragment extends DialogFragment {
    private MainActivityViewModel mainActivityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.date_time_picker, container, false);

        mainActivityViewModel = ViewModelProviders.of(getActivity(),
                new MainActivityViewModelFactory()).get(MainActivityViewModel.class);

        // Get current calendar date and time.
        Calendar currCalendar = Calendar.getInstance();

        DatePicker datePicker = (DatePicker) v.findViewById(R.id.date_picker);
        TimePicker timePicker = (TimePicker) v.findViewById(R.id.time_picker);
        Button set_button = v.findViewById(R.id.date_time_set);

        int year = currCalendar.get(Calendar.YEAR);
        int month = currCalendar.get(Calendar.MONTH);
        int day = currCalendar.get(Calendar.DAY_OF_MONTH);
        int hour = currCalendar.get(Calendar.HOUR_OF_DAY);
        int min = currCalendar.get(Calendar.MINUTE);

        datePicker.setMinDate(currCalendar.getTimeInMillis());
        timePicker.setHour(hour);
        timePicker.setMinute(min + 1);

        set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar expiry = Calendar.getInstance();
                expiry.set(Calendar.YEAR, datePicker.getYear());
                expiry.set(Calendar.MONTH, datePicker.getMonth());
                expiry.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                expiry.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                expiry.set(Calendar.MINUTE, timePicker.getMinute());

                if (expiry.getTimeInMillis() <= new Date().getTime()) {
                    //it's before current'
                    Toast.makeText(getActivity(), "Invalid time, please select a future date", Toast.LENGTH_SHORT).show();
                } else {
                    authenticate(new BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);
                            Toast.makeText(getContext(), "Authentication Succeeded", Toast.LENGTH_SHORT).show();
                            String dateString = String.valueOf(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(expiry.getTime()));
                            Log.i("MainActivity", "onAuthenticationSucceeded: Passsing date = " + dateString);
                            mainActivityViewModel.createCode(dateString);
                            getDialog().dismiss();
                        }
                    });
                }
            }
        });

        return v;
    }

    private void authenticate(BiometricPrompt.AuthenticationCallback callback) {
        Handler handler = new Handler();
        Executor executor = handler::post;

        BiometricManager biometricManager = BiometricManager.from(getContext());
        if(biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS){
            BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Generate Unlock Code")
                    .setDescription("Authenticate with biometrics to generate an unlock code now.")
                    .setNegativeButtonText("Don't generate code now")
                    .build();
            BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, callback);
            biometricPrompt.authenticate(promptInfo);
        } else {
            // No biometrics, give error message
            Toast.makeText(getActivity(), "Only phones with biometrics are currently supported", Toast.LENGTH_SHORT).show();
        }
    }
}
