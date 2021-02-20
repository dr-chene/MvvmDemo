package com.dr_chene.mvvmdemo.di;

import com.dr_chene.mvvmdemo.AppDataBase;
import com.dr_chene.mvvmdemo.remote.ArticleService;
import com.dr_chene.mvvmdemo.remote.RetrofitFactory;
import com.dr_chene.mvvmdemo.repository.ArticleRepository;
import com.dr_chene.mvvmdemo.viewmodel.ArticleViewModel;

public class Inject {

    private Inject() { }

    public static ArticleViewModel injectArticleViewModel() {
        return new ArticleViewModel(getArticleRepository());
    }

    private static ArticleRepository getArticleRepository() {
        return new ArticleRepository(
                AppDataBase.getInstance().getPageArticleDao(),
                RetrofitFactory.create(RetrofitFactory.NORMAL).create(ArticleService.class)
        );
    }

}
