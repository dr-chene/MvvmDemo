package com.dr_chene.mvvmdemo.remote;


import com.dr_chene.mvvmdemo.model.PageArticle;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ArticleService {

    @GET("/article/list/{page}/json")
    Flowable<NetRes<PageArticle>> getArticleByPage(
            @Path("page") int page
    );
}
