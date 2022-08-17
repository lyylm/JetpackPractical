package com.example.roommigration;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private LiveData<List<Word>> allWordsLive;
    private WordDao wordDao;

    public WordRepository(Context context) {
        WordDatabase wordDatabase = WordDatabase.getWordDatabase(context.getApplicationContext());
        this.wordDao = wordDatabase.getWordDao();
        this.allWordsLive = this.wordDao.getAllWordsLive();
    }

    public LiveData<List<Word>> getAllWordsLive() {
        return allWordsLive;
    }

    public void insertWords(Word... words){
        new InsertAsyncTask(this.wordDao).execute(words);
    }
    public void updateWords(Word... words){
        new UpdateAsyncTask(this.wordDao).execute(words);
    }

    public void deleteWords(Word... words){
        new DeleteWordsAsync(this.wordDao).execute(words);
    }

    public void deleteAllWords(){
        new DeleteAllAsyncTask(this.wordDao).execute();
    }

    static class InsertAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;

        public InsertAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            this.wordDao.insertWords(words);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;

        public UpdateAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            this.wordDao.updateWords();
            return null;
        }
    }

    static class DeleteWordsAsync extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;

        public DeleteWordsAsync(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            this.wordDao.deleteWords(words);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private  WordDao wordDao;

        public DeleteAllAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.wordDao.deleteAllWords();
            return null;
        }
    }
}
