package com.example.roombasicstep2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //    WordDao wordDao;
//    WordDatabase wordDatabase;
    WordViewModel wordViewModel;

    TextView textView;
    Button buttonInsert, buttonUpdate, buttonClear, buttonDelete;

    LiveData<List<Word>> allWordsLive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

//        wordDatabase = Room.databaseBuilder(this,WordDatabase.class,"word_database")
//                .allowMainThreadQueries()//强制允许再主线程执行
//                .build();
//        wordDatabase = WordDatabase.getDatabase(this);
//        wordDao = wordDatabase.getWordDao();

        //allWordsLive = wordDao.getAllWordsLive();
        allWordsLive = wordViewModel.getAllWordsLive();
        textView = findViewById(R.id.textViewNumber);
        allWordsLive.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                StringBuilder builder = new StringBuilder();
                for (Word word : words) {
                    builder.append(word);
                    builder.append("\r\n");
                }
                textView.setText(builder.toString());
            }
        });
        buttonInsert = findViewById(R.id.buttonInsert);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonClear = findViewById(R.id.buttonClear);
        buttonDelete = findViewById(R.id.buttonDelete);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word1 = new Word("Hello", "你好！");
                Word word2 = new Word("World", "世界！");
                //new InsertAsyncTask(wordDao).execute(word1,word2);
                //wordDao.insertWords(word1,word2);
                //updateView();
                wordViewModel.insertWords(word1, word2);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Hi", "呵呵！");
                word.setId(20);
                //wordDao.deleteWords(word);
                //updateView();
                //new DeleteAsyncTask(wordDao).execute(word);
                wordViewModel.deleteWords(word);
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Hi", "呵呵！");
                word.setId(20);
                //wordDao.updateWords(word);
                //updateView();
                //new UpdateAsyncTask(wordDao).execute(word);
                wordViewModel.updateWords(word);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //wordDao.deleteAllWords();
                //updateView();
                //new DeleteAllAsyncTask(wordDao).execute();
                wordViewModel.deleteAllWords();
            }
        });


    }

//    static class InsertAsyncTask extends AsyncTask<Word,Void,Void>{
//        private WordDao wordDao;
//
//        public InsertAsyncTask(WordDao wordDao) {
//            this.wordDao = wordDao;
//        }
//
//        @Override
//        protected Void doInBackground(Word... words) {
//            wordDao.insertWords(words);
//            return null;
//        }
//    }
//
//    static class UpdateAsyncTask extends AsyncTask<Word,Void,Void>{
//        private WordDao wordDao;
//
//        public UpdateAsyncTask(WordDao wordDao) {
//            this.wordDao = wordDao;
//        }
//
//        @Override
//        protected Void doInBackground(Word... words) {
//            this.wordDao.updateWords(words);
//            return null;
//        }
//    }
//
//    static class DeleteAsyncTask extends AsyncTask<Word,Void,Void>{
//        private WordDao wordDao;
//
//        public DeleteAsyncTask(WordDao wordDao) {
//            this.wordDao = wordDao;
//        }
//
//        @Override
//        protected Void doInBackground(Word... words) {
//            this.wordDao.deleteWords(words);
//            return null;
//        }
//    }
//
//    static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
//        private WordDao wordDao;
//
//        public DeleteAllAsyncTask(WordDao wordDao) {
//            this.wordDao = wordDao;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            this.wordDao.deleteAllWords();
//            return null;
//        }
//    }

//    static class QueryAllAsyncTask extends AsyncTask<Void,Void,Void>{
//        private WordDao wordDao;
//
//        public QueryAllAsyncTask(WordDao wordDao) {
//            this.wordDao = wordDao;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            allWordsLive = this.wordDao.getAllWordsLive();
//            return null;
//        }
//
//    }
}