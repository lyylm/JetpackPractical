package com.yett.lifecyclesdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {
    //旋转屏幕不会重新创建，就不会重置
    static MyChrometer myChrometer;
    //Chronometer chronometer;
    //计数器每次退出时所记录的时间
    //private long elapsedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        chronometer = findViewById(R.id.meter);
//        //Unix时间从1970，1，1凌晨算，到现在的毫秒时间
//        //System.currentTimeMillis();
//        //手机从本次启动到现在的时间
//        //SystemClock.elapsedRealtime();
//        chronometer.setBase(SystemClock.elapsedRealtime());
//        chronometer.start();

        myChrometer = findViewById(R.id.meter);
        getLifecycle().addObserver(myChrometer);
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        //当前的时间，减去计时器启动时的时间，就是从启动到现在计时器所记录的时间
//        elapsedTime = SystemClock.elapsedRealtime()-chronometer.getBase();
//        chronometer.stop();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        chronometer.setBase(SystemClock.elapsedRealtime()-elapsedTime);
//        chronometer.start();
//    }
}