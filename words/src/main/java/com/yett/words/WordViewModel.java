package com.yett.words;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordRepository wordRepository;
    public WordViewModel(@NonNull Application application) {
        super(application);
        this.wordRepository = new WordRepository(application);
    }

    public LiveData<List<Word>> getAllWordsLive(){
        return this.wordRepository.getAllWordsLive();
    }

    public void insertWords(Word... words){
        this.wordRepository.insertWords(words);
    }

    public void updateWords(Word... words){
        this.wordRepository.updateWords(words);
    }

    public void deleteWords(Word... words){
        this.wordRepository.deleteWords(words);
    }

    public void deleteAllWords(){
        this.wordRepository.deleteAllWords();
    }
}
