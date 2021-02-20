package com.dr_chene.mvvmdemo.repository;

import android.util.Log;

import com.dr_chene.mvvmdemo.NetworkBoundResource;
import com.dr_chene.mvvmdemo.model.PageArticle;
import com.dr_chene.mvvmdemo.model.PageArticleDao;
import com.dr_chene.mvvmdemo.remote.ArticleService;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.rxjava3.core.Single;

public class ArticleRepository extends NetworkBoundResource<PageArticle, PageArticle> {

    public static final String TAG = "ArticleRepository";

    private final PageArticleDao pageArticleDao;
    private final ArticleService pageArticleApi;
    private int curPage = 0;
    private boolean over = false;

    public ArticleRepository(PageArticleDao pageArticleDao, ArticleService pageArticleService) {
        this.pageArticleDao = pageArticleDao;
        this.pageArticleApi = pageArticleService;
    }

    public boolean refresh(RequestSuccess<PageArticle> success) {
        over = false;
        curPage = 0;
        return request(success);
    }

    public boolean load(RequestSuccess<PageArticle> success) {
        synchronized (this) {
            curPage++;
        }
        return request(success);
    }

//    public ArticleViewModel.RequestResult<PageArticle> refresh() {
//        boolean request = refreshArticles();
//        ArticleViewModel.RequestResult<PageArticle> res;
//        if (request) res = new ArticleViewModel.RequestResult<>(pageArticleDao.getArticleByPage(1)
//                .subscribeOn(Schedulers.io()), true);
//        else res = new ArticleViewModel.RequestResult<>(false);
//        return res;
//    }

//    public ArticleViewModel.RequestResult<PageArticle> load() {
//        boolean request = loadArticles();
//        ArticleViewModel.RequestResult<PageArticle> res;
//        if (request)
//            res = new ArticleViewModel.RequestResult<>(pageArticleDao.getArticleByPage(curPage)
//                    .subscribeOn(Schedulers.io()), true);
//        else res = new ArticleViewModel.RequestResult<>(false);
//        return res;
//    }

//    private boolean refreshArticles() {
//        return getArticleByPage(0);
//    }
//
//    private boolean loadArticles() {
//        return getArticleByPage(++curPage);
//    }

    //加载数据，思路是通过网络加载数据，如果失败则从本地加载数据
    //事实上我觉得这样的模式会消耗大量的流量，并且在网络优良的情况下本地room数据基本没用
    //我是考虑到用户既然要使用该app，就是想获得最新的数据(网上)，所以才使用了这样一个模式
//    private boolean getArticleByPage(int page) {
//        if (over) {
//            Toast.makeText(App.getContext(), "no more data", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (!App.connected) {
//            Toast.makeText(App.getContext(), "网络不可用", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        pageArticleApi.getArticleByPage(page)
//                .subscribe(new DisposableSingleObserver<ApiResponse<PageArticle>>() {
//                    @Override
//                    public void onSuccess(@NonNull ApiResponse<PageArticle> pageArticleNetRes) {
//                        if (pageArticleNetRes.errorCode != 0) {
//                            new Exception(pageArticleNetRes.errorMsg).printStackTrace();
//                        }
//                        over = pageArticleNetRes.data.over;
//                        Log.d(TAG, "onSuccess: " + pageArticleNetRes.data.datas.get(0));
//                        Log.d(TAG, "onSuccess: " + pageArticleNetRes.data.curPage);
//                        pageArticleDao.insertArticle(pageArticleNetRes.data).subscribe(new CompletableObserver() {
//                            @Override
//                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
//
//                            }
//
//                            @Override
//                            public void onComplete() {
//                                Log.d(TAG, "onComplete: insert success");
//                            }
//
//                            @Override
//                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//                                e.printStackTrace();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        e.printStackTrace();
//                    }
//                });
//        return true;
//    }

    @Override
    protected Completable saveCallResult(PageArticle item) {
        return pageArticleDao.insertArticle(item);
    }

    @Override
    protected boolean shouldFetch(PageArticle data) {
        return false;
    }

    @Override
    protected Flowable<List<PageArticle>> loadFromDb() {
        return pageArticleDao.getArticleByPage(curPage + 1);
    }

    @Override
    protected Single<ApiResponse<PageArticle>> createCall() {
        return pageArticleApi.getArticleByPage(curPage);
    }

    @Override
    protected boolean isApiRequestSuccess(ApiResponse<PageArticle> pageArticleApiResponse) {
        return pageArticleApiResponse.errorCode == 0;
    }

    @Override
    protected PageArticle remoteToLocal(PageArticle article) {
        return article;
    }

    @Override
    protected void insertSuccess() {
        Log.d(TAG, "onComplete: insert success");
    }

    @Override
    public void apiResultError(int error, String message) {
        Log.d(TAG, "apiResultError: error: " + error + " message: " + message);
    }

    @Override
    protected void localRequestSuccess(PageArticle data) {
        over = data.over;
    }

    @Override
    protected boolean isMore() {
        return !over;
    }
}
