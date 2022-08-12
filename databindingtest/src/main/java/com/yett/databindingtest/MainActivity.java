package com.yett.databindingtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yett.databindingtest.databinding.ActivityMainBinding;

/**
 * 使用DataBinding注意：
 * TODO 1、在使用模块的gradle中添加dataBinding {
 *             enabled true
 *         }
 * TODO 2、在布局代码界面添加 layout,并定义相关变量<layout xmlns:android="http://schemas.android.com/apk/res/android"
 *     xmlns:app="http://schemas.android.com/apk/res-auto"
 *     xmlns:tools="http://schemas.android.com/tools">
 *
 *     <data>
 *         <variable
 *             name="data"
 *             type="com.yett.databindingtest.MyViewModel" />
 *     </data>
 * TODO 3、在Activity界面
 */
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MyViewModel myViewModel;
//    Button button;
//    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO 这是使用DataBinding的区别  setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        /**
         * binding.textView访问布局中的控件
         */

//        button=findViewById(R.id.button);
//        textView=findViewById(R.id.textView);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
//        myViewModel.getNumber().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                textView.setText(String.valueOf(integer));
//            }
//        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myViewModel.add();
//            }
//        });
        binding.setData(myViewModel);//这个和布局文件中的data变量对应
        binding.setLifecycleOwner(this);
    }
}