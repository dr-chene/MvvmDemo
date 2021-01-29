package com.dr_chene.mvvmdemo.repository;

import android.util.Log;
import android.widget.Toast;

import com.dr_chene.mvvmdemo.App;
import com.dr_chene.mvvmdemo.bean.Article;
import com.dr_chene.mvvmdemo.model.PageArticle;
import com.dr_chene.mvvmdemo.model.PageArticleDao;
import com.dr_chene.mvvmdemo.remote.ArticleService;
import com.dr_chene.mvvmdemo.remote.NetRes;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ArticleRepository {
    private final PageArticleDao pageArticleDao;
    private final ArticleService pageArticleApi;
    private int curPage = 0;
    private boolean over = false;

    public ArticleRepository(PageArticleDao pageArticleDao, ArticleService pageArticleService) {
        this.pageArticleDao = pageArticleDao;
        this.pageArticleApi = pageArticleService;
    }

    public List<Article> refreshArticles() {
        return getArticleByPage(0);
    }

    public List<Article> loadArticles() {
        return getArticleByPage(++curPage);
    }

    //加载数据，思路是通过网络加载数据，如果失败则从本地加载数据
    //事实上我觉得这样的模式会消耗大量的流量，并且在网络优良的情况下本地room数据基本没用
    //我是考虑到用户既然要使用该app，就是想获得最新的数据(网上)，所以才使用了这样一个模式
    private List<Article> getArticleByPage(int page) {
        if (over) {
            Toast.makeText(App.getContext(), "no more data", Toast.LENGTH_SHORT).show();
            return null;
        }
        NetRes<PageArticle> netRes = pageArticleApi.getArticleByPage(page)
                .subscribeOn(Schedulers.io())
                .blockingLast();
        if (netRes.getErrorCode() != 0) {
            Toast.makeText(App.getContext(), netRes.getErrorMsg(), Toast.LENGTH_SHORT).show();
            return null;
        }
        PageArticle articles = netRes.getData();
        over = articles.over;
//        articles = null;
        if (articles == null) {
            //这有一个bug，app初始化就从room加载本地数据会显示找不到，成功显示ui后就不会
            //可以将PageArticleDao中的Maybe改为Single看看这个bug
            articles = pageArticleDao.getArticleByPage(page)
                    .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                    .blockingGet();
        } else {
            insertPageArticle(articles);
        }
        return articles == null ? null : articles.datas;
    }

    private void insertPageArticle(PageArticle article){
        pageArticleDao.insertArticle(article)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("TAG_d", "onSubscribe: " + d.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG_d", "onComplete: ");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG_d", "onError: " + e.getMessage());
                    }
                });
    }
}
