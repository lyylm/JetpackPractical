package com.yett.words2;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> allWordsLive;

    public WordRepository(Context context) {
        this.wordDao = WordDatabase.getWordDatabase(context).getWordDao();
        this.allWordsLive = wordDao.getAllWordsLive();
    }

    public LiveData<List<Word>> getAllWordsLive() {
        return allWordsLive;
    }

    public LiveData<List<Word>> getFilteredWordsLive(String patten){
        return this.wordDao.findAllWordsLive("%"+patten+"%");//模糊查询前后要加百分号
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
