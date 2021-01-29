package com.dr_chene.mvvmdemo.util;

import androidx.room.TypeConverter;

import com.dr_chene.mvvmdemo.bean.Article;
import com.google.gson.reflect.TypeToken;

import java.util.List;

//List的转换器，将bean中的List转换为string存入room，取的时候再自动转回来
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
