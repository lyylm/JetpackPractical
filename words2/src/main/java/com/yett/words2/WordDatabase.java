package com.yett.words2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase INSTANCE;
    static synchronized WordDatabase getWordDatabase(Context context){
        if (INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class, "word_database")
                    .build();
        }
        return INSTANCE;
    }

    public abstract WordDao getWordDao();
}
