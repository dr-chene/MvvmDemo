package com.dr_chene.mvvmdemo.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface PageArticleDao {

    @Query("SELECT * FROM page_articles WHERE cur_page = :page ")
    Flowable<List<PageArticle>> getArticleByPage(int page);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertArticle(PageArticle article);
}
