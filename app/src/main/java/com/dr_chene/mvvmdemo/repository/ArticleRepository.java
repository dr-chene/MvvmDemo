package com.dr_chene.mvvmdemo.repository;

import android.util.Log;
import android.widget.Toast;

import com.dr_chene.mvvmdemo.App;
import com.dr_chene.mvvmdemo.model.PageArticle;
import com.dr_chene.mvvmdemo.model.PageArticleDao;
import com.dr_chene.mvvmdemo.remote.ArticleService;
import com.dr_chene.mvvmdemo.remote.NetRes;
import com.dr_chene.mvvmdemo.viewmodel.ArticleViewModel;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ArticleRepository {

    public static final String TAG = "ArticleRepository";

    private final PageArticleDao pageArticleDao;
    private final ArticleService pageArticleApi;
    private int curPage = 0;
    private boolean over = false;

    public ArticleRepository(PageArticleDao pageArticleDao, ArticleService pageArticleService) {
        this.pageArticleDao = pageArticleDao;
        this.pageArticleApi = pageArticleService;
    }

    public ArticleViewModel.RequestResult<PageArticle> refresh() {
        boolean request = refreshArticles();
        ArticleViewModel.RequestResult<PageArticle> res;
        if (request) res = new ArticleViewModel.RequestResult<>(pageArticleDao.getArticleByPage(1)
                .subscribeOn(Schedulers.io()), true);
        else res = new ArticleViewModel.RequestResult<>(false);
        return res;
    }

    public ArticleViewModel.RequestResult<PageArticle> load() {
        boolean request = loadArticles();
        ArticleViewModel.RequestResult<PageArticle> res;
        if (request)
            res = new ArticleViewModel.RequestResult<>(pageArticleDao.getArticleByPage(curPage)
                    .subscribeOn(Schedulers.io()), true);
        else res = new ArticleViewModel.RequestResult<>(false);
        return res;
    }

    private boolean refreshArticles() {
        return getArticleByPage(0);
    }

    private boolean loadArticles() {
        return getArticleByPage(++curPage);
    }

    //加载数据，思路是通过网络加载数据，如果失败则从本地加载数据
    //事实上我觉得这样的模式会消耗大量的流量，并且在网络优良的情况下本地room数据基本没用
    //我是考虑到用户既然要使用该app，就是想获得最新的数据(网上)，所以才使用了这样一个模式
    private boolean getArticleByPage(int page) {
        if (over) {
            Toast.makeText(App.getContext(), "no more data", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!App.connected) {
            Toast.makeText(App.getContext(), "网络不可用", Toast.LENGTH_SHORT).show();
            return false;
        }
        pageArticleApi.getArticleByPage(page)
                .subscribe(new DisposableSingleObserver<NetRes<PageArticle>>() {
                    @Override
                    public void onSuccess(@NonNull NetRes<PageArticle> pageArticleNetRes) {
                        if (pageArticleNetRes.getErrorCode() != 0) {
                            new Exception(pageArticleNetRes.getErrorMsg()).printStackTrace();
                        }
                        over = pageArticleNetRes.getData().over;
                        Log.d(TAG, "onSuccess: " + pageArticleNetRes.getData().datas.get(0));
                        Log.d(TAG, "onSuccess: " + pageArticleNetRes.getData().curPage);
                        pageArticleDao.insertArticle(pageArticleNetRes.getData()).subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete: insert success");
                            }

                            @Override
                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                });
        return true;
    }
}
