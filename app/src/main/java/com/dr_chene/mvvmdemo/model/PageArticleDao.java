package com.dr_chene.mvvmdemo.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface PageArticleDao {

    @Query("SELECT * FROM page_articles WHERE cur_page = :page")
    Maybe<PageArticle> getArticleByPage(int page);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertArticle(PageArticle article);
}
