package com.example.roommigration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button buttonInsert,buttonClear;
    Switch aSwitch;
    RecyclerView recyclerView;

    WordViewModel wordViewModel;

    MyAdapter myAdapterCardView, myAdapterNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.buttonClear = findViewById(R.id.buttonClear);
        this.buttonInsert = findViewById(R.id.buttonInsert);
        this.aSwitch = findViewById(R.id.switch1);
        this.recyclerView = findViewById(R.id.recycleView);

        this.wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        myAdapterCardView = new MyAdapter(true);
        myAdapterNormal = new MyAdapter(false);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(myAdapterNormal);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    recyclerView.setAdapter(myAdapterCardView);
                }else {
                    recyclerView.setAdapter(myAdapterNormal);
                }
            }
        });

        this.wordViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                myAdapterCardView.setAllWords(words);
                myAdapterCardView.notifyDataSetChanged();
                myAdapterNormal.setAllWords(words);
                myAdapterNormal.notifyDataSetChanged();
            }
        });

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] english = {
                        "Hello",
                        "World",
                        "Android",
                        "Google",
                        "Studio",
                        "Project",
                        "Database",
                        "Recycler",
                        "View",
                        "String",
                        "Value",
                        "Integer"
                };
                String[] chinese = {
                        "你好",
                        "世界",
                        "安卓系统",
                        "谷歌",
                        "工作室",
                        "项目",
                        "数据库",
                        "回收站",
                        "视图",
                        "字符串",
                        "价值",
                        "整数类型"
                };
                for (int i=0;i<english.length;i++){
                    wordViewModel.insertWords(new Word(english[i],chinese[i]));
                }
            }
        });

        this.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordViewModel.deleteAllWords();
            }
        });
    }
}