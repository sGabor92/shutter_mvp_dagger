package hu.webandmore.shutter_mvp.utils;

import android.os.CountDownTimer;
import android.util.Log;

public class DiscoveryTimer {
    private static final String TAG = "TIMER";
    private final OnTimeoutListener mOnTimeoutListener;
    private CountDownTimer mTimer;

    public DiscoveryTimer(OnTimeoutListener onTimeoutListener, long seconds) {
        this.mOnTimeoutListener = onTimeoutListener;
        this.mTimer = createTimer(seconds);
    }

    public void start() {
        mTimer.start();
    }

    public void cancel() {
        mTimer.cancel();
    }

    public void reset() {
        mTimer.cancel();
        mTimer.start();
    }

    public void timeout(long seconds) {
        mTimer.cancel();
        mTimer = null;
        mTimer = createTimer(seconds);
    }

    private CountDownTimer createTimer(final long seconds) {
        return new CountDownTimer(1000 * seconds, 1000 * seconds) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "FINISH");
                mOnTimeoutListener.onNsdDiscoveryTimeout();
            }
        };
    }

    public interface OnTimeoutListener {
        void onNsdDiscoveryTimeout();
    }

}
