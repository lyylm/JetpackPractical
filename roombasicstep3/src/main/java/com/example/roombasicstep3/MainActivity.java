package com.example.roombasicstep3;

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
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private WordViewModel wordViewModel;
    Button buttonInsert,buttonClear;
    Switch aSwitch;

    RecyclerView recyclerView;
    MyAdapter myAdapter1,myAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        buttonClear = findViewById(R.id.buttonClear);
        buttonInsert = findViewById(R.id.buttonInsert);
        aSwitch = findViewById(R.id.switch1);

        this.wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        myAdapter1 = new MyAdapter(true);
        myAdapter2 = new MyAdapter(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter2);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    recyclerView.setAdapter(myAdapter1);
                }else {
                    recyclerView.setAdapter(myAdapter2);
                }
            }
        });

        this.wordViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
               myAdapter1.setAllWords(words);
               myAdapter1.notifyDataSetChanged();
               myAdapter2.setAllWords(words);
               myAdapter2.notifyDataSetChanged();
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
                        "??????",
                        "??????",
                        "????????????",
                        "??????",
                        "?????????",
                        "??????",
                        "?????????",
                        "?????????",
                        "??????",
                        "?????????",
                        "??????",
                        "????????????"
                };
                for (int i=0;i<english.length;i++){
                    wordViewModel.insertWords(new Word(english[i],chinese[i]));
                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordViewModel.deleteAllWords();
            }
        });
    }
}