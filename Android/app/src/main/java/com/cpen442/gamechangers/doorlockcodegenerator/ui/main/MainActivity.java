package com.cpen442.gamechangers.doorlockcodegenerator.ui.main;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cpen442.gamechangers.doorlockcodegenerator.R;
import com.cpen442.gamechangers.doorlockcodegenerator.data.LockRepository;
import com.cpen442.gamechangers.doorlockcodegenerator.data.Result;
import com.cpen442.gamechangers.doorlockcodegenerator.data.Result.Success;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.CodeInfo;
import com.cpen442.gamechangers.doorlockcodegenerator.data.model.Lock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MS_IN_SECOND      = 1000;
    private static final int MINUTES_IN_HOUR   = 60;
    private static final int HOURS_IN_DAY      = 24;
    private static final int DAYS_IN_MONTH     = 30;

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
    View mShowLog;

    @BindView(R.id.lock_icon)
    ImageView mLockIcon;

    @BindView(R.id.last_opened_label)
    TextView mLast_opened_label;

    @BindView(R.id.last_created_label)
    TextView mLast_created_label;

    @BindView(R.id.unlock_now_button)
    Button mGenerate_code_button;


    private MainActivityViewModel mViewModel;
    private List<Lock> mLocks;
    private long nextCodeDuration = 0;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

//        setSupportActionBar(mToolbar);

        mViewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory()).get(MainActivityViewModel.class);
        setupCallbacks();
        mViewModel.getLocks();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mViewModel.getSelectedLock() != null) {
            mViewModel.getCodeInfo();
        }
    }



    private void setupCallbacks() {
        mViewModel.getFetchLocksResult().observe(this, result -> {
            if (result instanceof Success) {
                mLocks = ((Success<List<Lock>>) result).getData();
                populateLocksDropdown(mLocks);

                // Get Code Info for the selected lock
//                if (mSelectedLock != null) {
//                    mViewModel.getCodeInfo(mSelectedLock.getId());
//                }
            } else {
                Toast.makeText(this, "Error fetching lock list", Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.getFetchCodeResult().observe(this, result-> {
            if (result instanceof Success) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                CodeInfo codeInfo = ((Success<CodeInfo>) result).getData();
                if (codeInfo == null || codeInfo.isExpired()) {
                    mCodeText.setText("[ LOCKED ]");
                    mTimeRemainingText.setText(R.string.time_remaining_idle_text);
                    mLockIcon.setImageResource(R.drawable.ic_lock_locked);
                    ImageViewCompat.setImageTintList(mLockIcon,
                            ColorStateList.valueOf(getColor(R.color.colorAccentDark)));

                } else {
                    try {
                        Date expiry_time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX")
                                .parse(codeInfo.getExpiry_time());
                        long duration = expiry_time.getTime() - new Date().getTime();
                        if (duration <= 0) {
                            mCodeText.setText("[ LOCKED ]");
                            mTimeRemainingText.setText(R.string.time_remaining_idle_text);
                            mLockIcon.setImageResource(R.drawable.ic_lock_locked);
                            ImageViewCompat.setImageTintList(mLockIcon,
                                    ColorStateList.valueOf(getColor(R.color.colorAccentDark)));


                        } else {
                            mLockIcon.setImageResource(R.drawable.ic_lock_unlocked);
                            ImageViewCompat.setImageTintList(mLockIcon,
                                    ColorStateList.valueOf(getColor(R.color.colorAccentLight)));
                            onCodeCreated(codeInfo.getCode());
                            countDownLock(duration);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                try {
                    if (codeInfo != null) {
                        if (codeInfo.getUsed_at_time() != null) {
                            Date used_at_time = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"))
                                    .parse(codeInfo.getUsed_at_time());
                            mLast_opened_label.setText(String.format("Code last opened " + getTimeFromNow(used_at_time)));
                        } else {
                            mLast_opened_label.setText("Lock last opened: haven't been opened before yet");
                        }
                    }
                    if (codeInfo.getCreation_time() != null) {
                        Date creation_time = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"))
                                .parse(codeInfo.getCreation_time());
                        mLast_created_label.setText(String.format("Code last created by user: "
                                + codeInfo.getCreated_by().getEmail() + " " + getTimeFromNow(creation_time)));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (result instanceof Result.Error) {
                Toast.makeText(getApplicationContext(),
                        ((Result.Error) result).getError(),
                        Toast.LENGTH_LONG).show();
            }
        });

        mViewModel.getAddLockResult().observe(this, result -> {
            if (result instanceof Success) {
                Lock newLock = ((Success<Lock>) result).getData();
                //mLocks.add(newLock);
                populateLocksDropdown(mLocks);
            } else if (result instanceof Result.Error) {
                Toast.makeText(getApplicationContext(),
                        ((Result.Error) result).getError(),
                        Toast.LENGTH_LONG).show();
            }
        });

        mViewModel.getCreateCodeResult().observe(this, result -> {
            if (result instanceof Success) {
                mViewModel.getCodeInfo();
            } else if (result instanceof Result.Error) {
                Toast.makeText(getApplicationContext(),
                        ((Result.Error) result).getError(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * @param duration duration in ms
     */
    private void countDownLock(long duration) {
        countDownTimer = new CountDownTimer(duration, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long msRemaining) {
                long seconds = msRemaining / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;
                String text = (seconds % 60) + "s remaining.";
                if (minutes > 0) {
                    text = (minutes % 60) + "m " + text;
                }
                if (hours > 0) {
                    text = (hours % 24) + "h " + text;
                }
                if (days > 0) {
                    text = days + "days " + text;
                }
                mTimeRemainingText.setText(text);
            }

            @Override
            public void onFinish() {
                mCodeText.setText("[ EXPIRED ]");
                mTimeRemainingText.setText(R.string.time_remaining_idle_text);
                mLockIcon.setImageResource(R.drawable.ic_lock_locked);
                ImageViewCompat.setImageTintList(mLockIcon,
                        ColorStateList.valueOf(getColor(R.color.colorAccentDark)));
            }
        }.start();
    }

    private void onCodeCreated(int code) {

        String codeText = "";
        for (int i = 0; i < 4; i++) {
            codeText = code % 10 + codeText;
            code /= 10;
        }
        mCodeText.setText(codeText);

    }

    private void populateLocksDropdown(List<Lock> locks) {
        List<Object> dropdownData = new ArrayList<>();
        dropdownData.addAll(locks);
        dropdownData.add("+ Add Lock");

        ArrayAdapter<Object> lockArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                dropdownData);

        mLockSelect.setAdapter(lockArrayAdapter);
        mLockSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object selected = parent.getItemAtPosition(pos);
                if (selected instanceof Lock) {
                    mViewModel.setSelectedLock((Lock) selected);
                    mViewModel.getCodeInfo();
                    //countDownLock(0);
                } else {
                    // Add new lock
                    DialogFragment dialogFragment = new AddLockDiaLogFragment();
                    dialogFragment.show(getSupportFragmentManager(), "addLockDialog");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        if (locks.size() > 0) {
            mViewModel.setSelectedLock((Lock) locks.get(0));
            mViewModel.getCodeInfo();
        }
    }



    @OnClick(R.id.unlock_now_button)
    void generateOwnCode() {
        DialogFragment dialogFragment = new DateTimePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "dateTimePicker");
    }

    @OnClick(R.id.guest_code_button)
    void generateGuestCode() {
    }

    public String getTimeFromNow(Date date) {
        long timeFromNow = System.currentTimeMillis() - date.getTime();

        timeFromNow /= MS_IN_SECOND;  // Into Seconds
        if (timeFromNow < SECONDS_IN_MINUTE) {
            return "just now";
        }

        timeFromNow /= SECONDS_IN_MINUTE;  // Into Minutes
        if (timeFromNow < MINUTES_IN_HOUR) {
            return "at " + timeFromNow + (timeFromNow == 1 ? " minute" : " minutes") + " ago";
        }

        timeFromNow /= MINUTES_IN_HOUR;  // Into Hours
        if (timeFromNow < HOURS_IN_DAY) {
            return "at " + timeFromNow + (timeFromNow == 1 ? " hour" : " hours") + " ago";
        }

        timeFromNow /= HOURS_IN_DAY;  // Into days
        if (timeFromNow < DAYS_IN_MONTH) {
            return "in " + timeFromNow + (timeFromNow == 1 ? " day" : " days") + " ago";
        }

        timeFromNow /= DAYS_IN_MONTH;  // Roughly months
        return "in " + timeFromNow + (timeFromNow == 1 ? " month" : " months") + " ago";
    }
}
