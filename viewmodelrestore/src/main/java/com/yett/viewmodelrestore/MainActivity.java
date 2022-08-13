package com.yett.viewmodelrestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.yett.viewmodelrestore.databinding.ActivityMainBinding;

/**
 * 使用ViewModel实现的状态保活
 * 方式一：使用onSaveInstanceState
 * 方式二：使用ViewModelSaveState
 */
public class MainActivity extends AppCompatActivity {
    public final static String KEY_NUMBER = "my_number";

    MyViewModel myViewModel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        //启动软件时读取数据，方式一
//        myViewModel=new ViewModelProvider(this).get(MyViewModel.class);
//        if (savedInstanceState!=null){
//            myViewModel.getNumber().setValue(savedInstanceState.getInt(KEY_NUMBER));
//        }
        //方式二//TODO 也不需要任何配置
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(this);
    }

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        //等情况将数据保存，方式一
//        outState.putInt(KEY_NUMBER,myViewModel.getNumber().getValue());
//    }
}