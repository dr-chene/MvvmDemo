package com.dr_chene.mvvmdemo.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dr_chene.mvvmdemo.bean.Article;
import com.dr_chene.mvvmdemo.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;

public class ArticleViewModel extends ViewModel {

    public static final String TAG = "ArticleViewModel";

    private final ArticleRepository repository;
    //    private Disposable disposable;
    //livedata的使用
    private final MutableLiveData<List<Article>> articles;

    public ArticleViewModel(ArticleRepository repository) {
        this.repository = repository;
        articles = new MutableLiveData<>();
    }

    public LiveData<List<Article>> getArticles() {
        return articles;
    }

    public boolean refreshArticles() {
        return repository.refresh(pageArticle -> {
            articles.postValue(pageArticle.datas);
        });
    }

    public boolean loadArticles(List<Article> curList) {
        return repository.load(pageArticle -> {
            List<Article> list = new ArrayList<>(curList);
            list.addAll(pageArticle.datas);
            articles.postValue(list);
        });
    }

//    public boolean refreshArticles() {
//        RequestResult<PageArticle> result = repository.refresh();
//        if (!result.result) return false;
//        disposable = result.flow.subscribe((article) -> {
//            Log.d(TAG, "accept: refresh");
//            articles.postValue(article.datas);
//            cancel();
//        });
//        return true;
//    }
//
//    public boolean loadArticles(List<Article> curList) {
//        RequestResult<PageArticle> result = repository.load();
//        if (!result.result) return false;
//        disposable = result.flow.subscribe((article) -> {
//            Log.d(TAG, "accept: load");
//            List<Article> list = new ArrayList<>(curList);
//            list.addAll(article.datas);
//            articles.postValue(list);
//            cancel();
//        });
//        return true;
//    }
//
//    private synchronized void cancel(){
//        if (disposable != null && !disposable.isDisposed()){
//            disposable.dispose();
//            disposable = null;
//        }
//    }
//
//    public static class RequestResult<T>{
//        Flowable<T> flow;
//        boolean result;
//
//        public RequestResult(boolean result) {
//            this.result = result;
//        }
//
//        public RequestResult(Flowable<T> flow, boolean result) {
//            this.flow = flow;
//            this.result = result;
//        }
//    }
}