package com.cpen442.gamechangers.doorlockcodegenerator.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;

import com.cpen442.gamechangers.doorlockcodegenerator.R;
import com.cpen442.gamechangers.doorlockcodegenerator.data.Result;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.Lock;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int DEFAULT_DURATION = 1000 * 60 * 3;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.code_label)
    TextView mCodeText;

    @BindView(R.id.time_remaining_label)
    TextView mTimeRemainingText;

    @BindView(R.id.select_lock)
    Spinner mLockSelect;

    @BindView(R.id.show_log)
    Button mShowLog;

    private Lock mSelectedLock = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        //TODO call populate locks dropdown with lock list


    }

    /**
     * @param duration duration in ms
     */
    private void countDownLock(long duration) {
        new CountDownTimer(duration, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long msRemaining) {
                long seconds = msRemaining / 1000;
                long minutes = seconds / 60;
                mTimeRemainingText.setText(seconds + "s " + minutes + "m remaining.");
            }

            @Override
            public void onFinish() {
                mTimeRemainingText.setText(R.string.time_remaining_idle_text);
            }
        };
    }

    private void populateLocksDropdown(List<Lock> locks) {
        List<Object> dropdownData = new ArrayList<>();
        dropdownData.add(locks);
        dropdownData.add("+ Add Lock");

        ArrayAdapter<Object> lockArrayAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                dropdownData);
        mLockSelect.setAdapter(lockArrayAdapter);
        mLockSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object selected = parent.getItemAtPosition(pos);
                if (selected instanceof Lock) {
                    mSelectedLock = (Lock) selected;
                    mToolbar.setTitle(mSelectedLock.getDisplay_name());
                } else {
                    // Add new lock
                    DialogFragment dialogFragment = new AddLockDiaLogFragment();
                    dialogFragment.show(getSupportFragmentManager(), "addLockDialog");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//        mainActivityViewModel.getFetchLocksResult().observe(this,
//                new Observer<Result<List<Lock>>>() {
//            @Override
//            public void onChanged(Result<List<Lock>> result) {
//                if (result instanceof Result.Success) {
//                    List<Lock> locks = ((Result.Success<List<Lock>>) result).getData();
//                    lockListAdapter = new LockListAdapter(locks);
//                    recyclerView.setAdapter(lockListAdapter);
//                } else if (result instanceof Result.Error) {
//                    Toast.makeText(getApplicationContext(),
//                            ((Result.Error) result).getError(),
//                            Toast.LENGTH_LONG).show();
//                }
            }
//        });
//
//        mainActivityViewModel.getAddLockResult().observe(this, new Observer<Result<Lock>>() {
//            @Override
//            public void onChanged(Result<Lock> result) {
//                if (result instanceof Result.Success) {
//                    lockListAdapter.notifyDataSetChanged();
//                } else if (result instanceof Result.Error) {
//                    Toast.makeText(getApplicationContext(),
//                            ((Result.Error) result).getError(),
//                            Toast.LENGTH_LONG).show();
//                }
//            }
        });
    }

    private void authenticate(BiometricPrompt.AuthenticationCallback callback) {
        BiometricManager biometricManager = BiometricManager.from(this);
        if(biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS){
            BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Generate Unlock Code")
                    .setDescription("Authenticate with biometrics to generate an unlock code now.")
                    .setNegativeButtonText("Don't generate code now")
                    .build();
            BiometricPrompt biometricPrompt = new BiometricPrompt(this, null, callback);
        } else {
            // No biometrics, give error message
            Toast.makeText(this, "Only phones with biometrics are currently supported", Toast.LENGTH_SHORT).show();

        }
    }



    @OnClick(R.id.unlock_now_button)
    void generateOwnCode() {
        getCode(DEFAULT_DURATION);
    }

    @OnClick(R.id.guest_code_button)
    void generateGuestCode() {
    }

    private void getCode(final long duration) {
        authenticate(new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "Authentication Succeeded", Toast.LENGTH_SHORT).show();

                //TODO Request code for default time

                countDownLock(duration);
            }
        });

    }
}
