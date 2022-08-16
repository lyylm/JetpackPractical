package com.example.roombasicstep3;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> allWordsLive;

    public WordRepository(Context context) {
        WordDatabase wordDatabase = WordDatabase.getWordDataBase(context.getApplicationContext());
        this.wordDao = wordDatabase.getWordDao();
        allWordsLive = this.wordDao.getAllWordsLive();
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
        new DeleteWrodsAsyncTask(this.wordDao).execute(words);
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
            this.wordDao.updateWords(words);
            return null;
        }
    }

    static class DeleteWrodsAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;

        public DeleteWrodsAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            this.wordDao.deleteWords(words);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private WordDao wordDao;

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
