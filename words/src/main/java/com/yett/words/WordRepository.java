package com.yett.words;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    LiveData<List<Word>> allWordsLive;
    WordDao wordDao;

    public WordRepository(Context context) {
        WordDatabase wordDatabase = WordDatabase.getWordDatabase(context);
        this.wordDao = wordDatabase.getWordDao();
        this.allWordsLive = this.wordDao.getAllWordsLive();
    }

    public LiveData<List<Word>> getAllWordsLive() {
        return allWordsLive;
    }

    public LiveData<List<Word>> findWordsWithPatten(String patten){
        //模糊查询就需要前后添加%
        return this.wordDao.findWordsWithPatten("%"+patten+"%");
    }

    public void insertWords(Word... words){
        new InsertAsyncTask(wordDao).execute(words);
    }

    public void updateWords(Word... words){
        new UpdateAsyncTask(wordDao).execute(words);
    }

    public void deleteWords(Word... words){
        new DeleteWordsAsyncTask(wordDao).execute(words);
    }

    public void deleteAllWords(){
        new DeleteAllAsyncTask(wordDao).execute();
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

    static class DeleteWordsAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;

        public DeleteWordsAsyncTask(WordDao wordDao) {
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
