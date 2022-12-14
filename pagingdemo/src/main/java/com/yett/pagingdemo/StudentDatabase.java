package com.yett.pagingdemo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class StudentDatabase extends RoomDatabase {
    private static StudentDatabase Instance;
    static synchronized StudentDatabase getInstance(Context context){
        if (Instance==null){
            Instance = Room.databaseBuilder(context,StudentDatabase.class,"student_database")
                    .build();
        }
        return Instance;
    }

    public abstract StudentDao getStudentDao();
}
