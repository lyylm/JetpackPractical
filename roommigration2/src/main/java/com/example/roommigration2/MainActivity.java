package com.example.roommigration2;

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
    RecyclerView recyclerView;
    Button buttonInsert,buttonClear;
    Switch aSwitch;
    WordViewModel wordViewModel;

    MyAdapter myAdapterCard, myAdapterNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        this.buttonClear = findViewById(R.id.buttonClear);
        this.buttonInsert = findViewById(R.id.buttonInsert);
        this.aSwitch = findViewById(R.id.switch1);

        this.wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        this.myAdapterCard = new MyAdapter(true, wordViewModel);
        this.myAdapterNormal = new MyAdapter(false, wordViewModel);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(myAdapterNormal);
        this.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    recyclerView.setAdapter(myAdapterCard);
                }else {
                    recyclerView.setAdapter(myAdapterNormal);
                }
            }
        });

        this.wordViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                int temp = myAdapterNormal.getItemCount();
                myAdapterCard.setAllWords(words);
                myAdapterNormal.setAllWords(words);
                if (temp!=words.size()){
                    myAdapterCard.notifyDataSetChanged();
                    myAdapterNormal.notifyDataSetChanged();
                }
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