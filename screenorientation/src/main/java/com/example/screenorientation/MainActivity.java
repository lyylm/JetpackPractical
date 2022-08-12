package com.example.screenorientation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 功能1：手机屏幕旋转，切换新的布局视图
 * TODO 注意添加横屏步骤：右键layout-》layout resource file -》file名称和竖屏的一致选择orietation->lanscape
 * TODO 且横屏和竖屏中控件的命名要一致，否则会出现空引用的情况【或者多出一套变量来进行处理，但是没有测试，感觉这种更好，但是比较麻烦】
 *
 * 功能2：手机横竖屏切换的时候会将activity销毁重建，所以数据会被重置，所以需要数据保活。【本次使用Bundle savedInstanceState方式】
 *
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lyy";
    TextView textView;
    Button button;
    Button button_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        if (savedInstanceState!=null){
            String s = savedInstanceState.getString("key");
            textView.setText(s);
        }

        button = findViewById(R.id.button8);
        //button_2 = findViewById(R.id.button8);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(R.string.Button2);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key",textView.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}