package com.yett.lifecyclesdemo;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Chronometer;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyChrometer extends Chronometer implements LifecycleObserver {
    private static String TAG = "lyy";
    private long elapsedTime;
    public MyChrometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void pauseMeter(){
        elapsedTime = SystemClock.elapsedRealtime()-getBase();
        stop();
        Log.d(TAG, "pauseMeter: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void resumeMeter(){
        setBase(SystemClock.elapsedRealtime()-elapsedTime);
        start();
        Log.d(TAG, "resumeMeter: ");
    }
}
