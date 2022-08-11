package com.example.screenorientation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * 手机屏幕旋转，切换新的布局视图
 * 注意添加横屏步骤：右键layout-》layout resource file -》file名称和竖屏的一致选择orietation->lanscape即可
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}