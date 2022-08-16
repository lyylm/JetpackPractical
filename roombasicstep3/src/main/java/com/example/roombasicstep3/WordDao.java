package com.example.roombasicstep3;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {
    @Insert
    void insertWords(Word... words);

    @Update
    void updateWords(Word... words);

    @Delete
    void deleteWords(Word... words);

    @Query("DELETE FROM Word")
    void deleteAllWords();

    @Query("SELECT * FROM Word ORDER BY ID DESC")
    LiveData<List<Word>> getAllWordsLive();
}
