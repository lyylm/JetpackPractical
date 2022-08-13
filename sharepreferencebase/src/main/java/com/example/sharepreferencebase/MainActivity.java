package com.example.sharepreferencebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
    }

    public void btn1Click(View view) {
        //这是一个共享的数据，系统会给一个默认的名称，这个是该activity所特有
        SharedPreferences shp = getPreferences(Context.MODE_PRIVATE);
        //写入
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt("number",100);
        //需要进行提交，提交有以下两种方式
        //editor.commit();//这个是同步的方式，如果多个对象同时执行会有冲突，不推荐使用
        editor.apply();//这个是异步的方式进行提交，推荐使用，不会出现冲突

        //读取
        int temp = shp.getInt("number",0);
        textView.setText("方式一保存的值是："+String.valueOf(temp));
    }

    public void btn2Click(View view) {
        //这是一个共享的数据，需要我们命名，这个数据可以被其他的对象，插件，固件等使用
        //SharedPreferences shp = getPreferences("my_data",Context.MODE_PRIVATE)
    }

    public void btn3Click(View view) {
    }
}