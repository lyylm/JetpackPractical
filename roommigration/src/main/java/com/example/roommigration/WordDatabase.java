package com.example.roommigration;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.Entity;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

/**
 * 数据库的移植：
 * 第一，修改数据库的结构，新添加的字段一定要为其添加get和Set函数，
 * 第二，修改数据库的版本，
 * 第三，选择移植的模式
 */
@Database(entities = {Word.class}, version = 4, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase INSTANCE;

    static synchronized WordDatabase getWordDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class, "word_database")
                    //.fallbackToDestructiveMigration()//暴力移植，会删除原有所有数据，重新创建新结构的数据表
                    //.addMigrations(MIGRATION_2_3)//这个是在保留原有数据的基础上进行数据库的移植
                    .addMigrations(MIGRATION_3_4)//删除字段的数据库移植
                    .build();
        }
        return INSTANCE;
    }

    public abstract WordDao getWordDao();
    //增加一个字段的移植
    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //因为数据库没有boolean变量，是用整型表示的
            database.execSQL("ALTER TABLE WORD ADD COLUMN bar_data INTEGER NOT NULL DEFAULT 1");
        }
    };

    //删除表数据结构的两个字段
    static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //按照新的表结构创建一个临时的表【数据库没有字符串类型，字符串类型用TEXT表示】
            database.execSQL("CREATE TABLE word_temp (id INTEGER PRIMARY KEY NOT NULL, english_word TEXT," +
                    "chinese_meaning TEXT)");
            //将原有表中的数据导入到临时表中
            database.execSQL("INSERT INTO word_temp (id, english_word,chinese_meaning) " +
                    "SELECT id,english_word,chinese_meaning FROM word");
            //删除原有表
            database.execSQL("DROP TABLE WORD");
            //将临时表改为原有表的名称
            database.execSQL("ALTER TABLE word_temp RENAME TO word");
        }
    };
}
