package com.yett.roombasic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    WordDao wordDao;
    WordDatabase wordDatabase;

    Button buttonInsert,buttonUpdate,buttonDelete,buttonClear;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordDatabase = Room.databaseBuilder(this,WordDatabase.class,"word_database")
                .allowMainThreadQueries()//强制允许在主线程执行
                .build();
        wordDao = wordDatabase.getWordDao();

        buttonInsert = findViewById(R.id.buttonInsert);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonClear = findViewById(R.id.buttonClear);
        buttonDelete = findViewById(R.id.buttonDelete);

        textView = findViewById(R.id.textViewNumber);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word1 = new Word("Hello","你好！");
                Word word2 = new Word("World","世界！");
                wordDao.insertWords(word1,word2);
                updateView();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordDao.deleteAllWords();
                updateView();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Hi","你好啊！");
                word.setId(6);
                wordDao.updateWords(word);
                updateView();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Hehe","呵呵");
                word.setId(16);
                wordDao.deleteWords(word);
                updateView();
            }
        });
    }

    void updateView(){
        List<Word> list = wordDao.getAllWords();
        StringBuilder builder = new StringBuilder();
        for (int i=0;i<list.size();i++){
            builder.append(list.get(i));
            builder.append("\r\n");
        }
        textView.setText(builder.toString());
    }
}