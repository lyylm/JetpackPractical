package com.yett.jsondemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yett.jsondemo.databinding.ActivityMainBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        //将对象转换成Json字符串
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student("Tom",23);
                Gson gson = new Gson();
                String jsonStudent = gson.toJson(student);
            }
        });
        //将Json字符串转换成对象
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jsonStr = "{\"age\": 23, \"student_name\": \"Tom\"}";
                Gson gson = new Gson();
                Student student = gson.fromJson(jsonStr, Student.class);
            }
        });
        //将复杂对象转换成json字符串
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student2 student2 = new Student2("Jack",23,new Score(89,78,90));
                Gson gson = new Gson();
                String json = gson.toJson(student2);
            }
        });
        //将json对象转换成复杂对象
        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = "{\"age\":23,\"name\":\"Jack\",\"score\":{\"chinese\":89,\"english\":90,\"math\":78}}";
                Gson gson = new Gson();
                Student2 student2 = gson.fromJson(json,Student2.class);
            }
        });

        //将数组对象转换成json字符串
        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student2 student21 = new Student2("Mike",20,new Score(89,88,90));
                Student2 student22 = new Student2("Lisa",19,new Score(97,86,90));
                Student2[] student2s = {student21,student22};
                Gson gson = new Gson();
                String json = gson.toJson(student2s);
            }
        });
        //将json字符串转换成数组对象
        binding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = "[{\"age\":20,\"name\":\"Mike\",\"score\":{\"chinese\":89,\"english\":90,\"math\":88}},{\"age\":19,\"name\":\"Lisa\",\"score\":{\"chinese\":97,\"english\":90,\"math\":86}}]";
                Gson gson = new Gson();
                Student2[] student2s = gson.fromJson(json,Student2[].class);
            }
        });

        //将List对象转换成json字符串
        binding.button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student2 student21 = new Student2("Mike",20,new Score(89,88,90));
                Student2 student22 = new Student2("Lisa",19,new Score(97,86,90));
                List<Student2> student2List = new ArrayList<>();
                student2List.add(student21);
                student2List.add(student22);
                String json = new Gson().toJson(student2List);
            }
        });
        //将Json字符串转换成List对象
        binding.button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = "[{\"age\":20,\"name\":\"Mike\",\"score\":{\"chinese\":89,\"english\":90,\"math\":88}},{\"age\":19,\"name\":\"Lisa\",\"score\":{\"chinese\":97,\"english\":90,\"math\":86}}]";
                List<Student2> student2List= Arrays.asList(new Gson().fromJson(json,Student2[].class));
                Type typeStudents = new TypeToken<Student2>(){}.getType();
                List<Student2> student2List1 = new ArrayList<>();
                student2List1 = new Gson().fromJson(json,typeStudents);//失败的，会报错
            }
        });
    }
}














