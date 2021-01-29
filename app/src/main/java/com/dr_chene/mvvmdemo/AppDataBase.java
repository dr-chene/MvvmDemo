package com.dr_chene.mvvmdemo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dr_chene.mvvmdemo.model.PageArticle;
import com.dr_chene.mvvmdemo.model.PageArticleDao;

//room数据库,使用了单例模式
@Database(entities = {PageArticle.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract PageArticleDao getPageArticleDao();

    private static volatile AppDataBase instance;
    //数据库名字：name + .db
    public static final String DATA_BASE_NAME = "demo.db";

    public static AppDataBase getInstance() {
        if (instance == null) {
            synchronized (AppDataBase.class) {
                if (instance == null) {
                    instance = buildDataBase(App.getContext());
                }
            }
        }
        return instance;
    }

    private static AppDataBase buildDataBase(Context context) {
        return Room.databaseBuilder(context, AppDataBase.class, DATA_BASE_NAME).build();
    }
}
