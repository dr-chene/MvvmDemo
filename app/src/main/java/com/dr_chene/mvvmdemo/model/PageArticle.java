package com.dr_chene.mvvmdemo.model;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.dr_chene.mvvmdemo.bean.Article;
import com.dr_chene.mvvmdemo.remote.Page;
import com.dr_chene.mvvmdemo.util.ArticleListTypeConverter;

import java.util.List;

@Entity(tableName = "page_articles")
@TypeConverters(ArticleListTypeConverter.class)
public class PageArticle extends Page {
    public List<Article> datas;
}
