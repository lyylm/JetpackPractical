package com.example.roombasicstep2;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
//    private WordDao wordDao;
//    private LiveData<List<Word>> allWordsLive;
    WordRepository wordRepository;

    public WordViewModel(@NonNull Application application) {
        super(application);
//        WordDatabase wordDatabase = WordDatabase.getDatabase(application);
//        wordDao = wordDatabase.getWordDao();
//        allWordsLive = wordDao.getAllWordsLive();
        wordRepository = new WordRepository(application);
    }

    public LiveData<List<Word>> getAllWordsLive() {
        //return allWordsLive;
        return wordRepository.getAllWordsLive();
    }

    public void insertWords(Word... words){
        //new InsertAsyncTask(wordDao).execute(words);
        wordRepository.insertWords(words);
    }

    public void updateWords(Word... words){
        //new UpdateAsyncTask(wordDao).execute(words);
        wordRepository.updateWords(words);
    }

    public void deleteWords(Word... words){
        //new DeleteAsyncTask(wordDao).execute(words);
        wordRepository.deleteWords(words);
    }

    public void deleteAllWords(){
        //new DeleteAllAsyncTask(wordDao).execute();
        wordRepository.deleteAllWords();
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
//            this.wordDao.insertWords(words);
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
}
