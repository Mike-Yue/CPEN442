package com.cpen442.gamechangers.doorlockcodegenerator.ui.main;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cpen442.gamechangers.doorlockcodegenerator.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.code_label)
    TextView mCodeText;

    @BindView(R.id.time_remaining_label)
    TextView mTimeRemainingText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);


    }

    /**
     *
     * @param duration duration in ms
     */
    private void countDownLock(long duration) {
        new CountDownTimer(duration, 1000) {

            @Override
            public void onTick(long msRemaining) {
                long seconds = msRemaining / 1000;
                long minutes = seconds / 60;
                if (minutes < 1) {
                    mTimeRemainingText.setText(seconds + "seconds remaining.");
                } else {
                    mTimeRemainingText.setText(minutes + "minutes remaining.");
                }
            }

            @Override
            public void onFinish() {
                mTimeRemainingText.setText(R.string.time_remaining_idle_text);
            }
        };
    }



    @OnClick(R.id.unlock_now_button)
    void generateOwnCode() {

    }

    @OnClick(R.id.guest_code_button)
    void generateGuestCode() {

    }
}
