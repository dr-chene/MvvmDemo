package com.dr_chene.mvvmdemo.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dr_chene.mvvmdemo.bean.Article;
import com.dr_chene.mvvmdemo.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;

public class ArticleViewModel extends ViewModel {
    private final ArticleRepository repository;
    //livedata的使用
    private final MutableLiveData<List<Article>> articles;

    public ArticleViewModel(ArticleRepository repository) {
        this.repository = repository;
        this.articles = new MutableLiveData<>();
    }

    public LiveData<List<Article>> getArticles() {
        return articles;
    }

    public boolean refreshArticles() {
        List<Article> res = repository.refreshArticles();
        if (res == null) return false;
        articles.postValue(res);
        return true;
    }

    public boolean loadArticles(@NonNull List<Article> curList) {
        List<Article> res = repository.loadArticles();
        if (res == null){
            return false;
        }
        ArrayList<Article> list = new ArrayList<>(curList);
        list.addAll(res);
        articles.postValue(list);
        return true;
    }
}
