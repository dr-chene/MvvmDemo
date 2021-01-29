package com.dr_chene.mvvmdemo.util;

import androidx.room.TypeConverter;

import com.dr_chene.mvvmdemo.bean.Article;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ArticleListTypeConverter {

    @TypeConverter
    public List<Article> stringToList(String s) {
        return GsonSingleton.getInstance().fromJson(s, new TypeToken<List<Article>>() {
        }.getType());
    }

    @TypeConverter
    public String listToString(List<Article> list) {
        return GsonSingleton.getInstance().toJson(list);
    }
}
