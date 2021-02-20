package com.dr_chene.mvvmdemo.view;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dr_chene.mvvmdemo.App;
import com.dr_chene.mvvmdemo.R;
import com.dr_chene.mvvmdemo.bean.Article;
import com.dr_chene.mvvmdemo.databinding.ActivityMainBinding;
import com.dr_chene.mvvmdemo.di.Inject;
import com.dr_chene.mvvmdemo.view.adapter.ArticleRecyclerViewAdapter;
import com.dr_chene.mvvmdemo.viewmodel.ArticleViewModel;

import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    public static final String TAG = "MainActivity";

    private ArticleRecyclerViewAdapter adapter;
    private ArticleViewModel viewModel;

    @Override
    public void initView(ActivityMainBinding binding) {
        viewModel = Inject.injectArticleViewModel();
        binding.swipeRefreshMainArticle.setRefreshing(true);
        adapter = new ArticleRecyclerViewAdapter();
        binding.rvMainArticle.setAdapter(adapter);
        Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show();
        refresh();
    }

    @Override
    public void initAction(ActivityMainBinding binding) {
        binding.swipeRefreshMainArticle.setOnRefreshListener(this::refresh);
        setLoadAction(binding.rvMainArticle, this::load);
        binding.fabArticleChange.setOnClickListener((v) -> {
            adapter.submitList(null);
        });
    }

    @Override
    public void subscribe(ActivityMainBinding binding) {
        viewModel.getArticles().observe(this, (list) -> {
                binding.swipeRefreshMainArticle.setRefreshing(false);
                binding.load.getRoot().setVisibility(View.INVISIBLE);
                adapter.submitList(list);
        });
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_main;
    }

    private void refresh() {
        if (!viewModel.refreshArticles()){
            getBinding().swipeRefreshMainArticle.setRefreshing(false);
            Toast.makeText(App.getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
        }
        autoCancel();
    }

    private void load() {
        getBinding().load.getRoot().setVisibility(View.VISIBLE);
        if (!viewModel.loadArticles(adapter.getCurrentList())){
            getBinding().load.getRoot().setVisibility(View.INVISIBLE);
            Toast.makeText(App.getContext(), "加载失败", Toast.LENGTH_SHORT).show();
        }
        autoCancel();
    }


    private boolean isSlideToBottom(RecyclerView view) {
        return view.computeVerticalScrollExtent() + view.computeVerticalScrollOffset()
                >= view.computeVerticalScrollRange();
    }

    private void autoCancel(){
        new Handler(getMainLooper()).postDelayed(() -> {
            if (getBinding().swipeRefreshMainArticle.isRefreshing()){
                getBinding().swipeRefreshMainArticle.setRefreshing(false);
            }
            if (getBinding().load.getRoot().getVisibility() == View.VISIBLE){
                getBinding().load.getRoot().setVisibility(View.INVISIBLE);
            }
        },5000);
    }

    private void setLoadAction(RecyclerView view, LoadAction action) {
        view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING && isSlideToBottom(recyclerView)) {
                    action.action();
                }
            }
        });
    }

    interface LoadAction {
        void action();
    }
}