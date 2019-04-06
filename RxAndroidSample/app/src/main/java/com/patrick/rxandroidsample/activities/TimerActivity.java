package com.patrick.rxandroidsample.activities;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.patrick.rxandroidsample.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * TimerTask 사용 예
 */
public class TimerActivity extends AppCompatActivity {
    // Timer 용
    private static final int DELAY = 0;
    private static final int PERIOD = 1000;

    // Countdown 용
    private static final int MILLISINFUTURE = 11 * 1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;

    private Unbinder mUnbinder;

    private Timer mTimer;
    private int mCount;

    private CountDownTimer mCountDownTimer;

    private Handler mHandler;

    @BindView(R.id.textViewTimer)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mUnbinder = ButterKnife.bind(this);
        initCountDownTask();
        initHandler();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
        if (mUnbinder != null)
            mUnbinder.unbind();
    }

    @OnClick(R.id.buttonTimer)
    void timerTask() {
        stop();
        timerStart();
    }

    @OnClick(R.id.buttonCountdown)
    void countDownTask() {
        stop();
        countDownTimerStart();
    }

    @OnClick(R.id.buttonHandler)
    void handlerTask() {
        stop();
        handlerStart();
    }

    public void timerStart() {
        mCount = 0;
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> mTextView.setText(String.valueOf(mCount++)));
            }
        }, DELAY, PERIOD);
    }

    public void timerStop() {
        if (mTimer != null)
            mTimer.cancel();
    }

    public void stop() {
        timerStop();
        countDownTimerStop();
        handlerStop();
    }

    private void initCountDownTask() {
        mCountDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTextView.setText(String.valueOf(mCount--));
            }

            @Override
            public void onFinish() {
                mTextView.setText("Finish .");
            }
        };
    }

    private void countDownTimerStart() {
        mCount = 10;
        mCountDownTimer.start();
    }

    private void countDownTimerStop() {
        if (mCountDownTimer != null)
            mCountDownTimer.cancel();
    }

    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            mTextView.setText(String.valueOf(mCount++));
            mHandler.postDelayed(this, 1000);
        }
    };

    private void initHandler() {
        mHandler = new Handler();
    }

    private void handlerStart() {
        mCount = 0;
        mHandler.postDelayed(timer, 0);
    }

    private void handlerStop() {
        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);
    }
}
