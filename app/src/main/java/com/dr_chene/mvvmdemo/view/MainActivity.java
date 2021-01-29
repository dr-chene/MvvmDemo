package com.dr_chene.mvvmdemo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private ArticleRecyclerViewAdapter adapter;
    private ArticleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = Inject.injectArticleViewModel();

        initView();
        initAction();
        subscribe();
    }

    private void initView() {
        mainBinding.swipeRefreshMainArticle.setRefreshing(true);
        adapter = new ArticleRecyclerViewAdapter();
        mainBinding.rvMainArticle.setAdapter(adapter);
        refresh();
    }

    private void initAction() {
        mainBinding.swipeRefreshMainArticle.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        setLoadAction(mainBinding.rvMainArticle, new LoadAction() {
            @Override
            public void action() {
                load();
            }
        });
        mainBinding.fabArticleChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.submitList(null);
            }
        });
    }

    private void subscribe() {
        viewModel.getArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> list) {
                mainBinding.swipeRefreshMainArticle.setRefreshing(false);
                mainBinding.load.getRoot().setVisibility(View.INVISIBLE);
                adapter.submitList(list);
            }
        });
    }

    private void refresh() {
        if (!viewModel.refreshArticles()){
            Toast.makeText(App.getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void load() {
        mainBinding.load.getRoot().setVisibility(View.VISIBLE);
        if (!viewModel.loadArticles(adapter.getCurrentList())){
            Toast.makeText(App.getContext(), "加载失败", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isSlideToBottom(RecyclerView view) {
        return view.computeVerticalScrollExtent() + view.computeVerticalScrollOffset()
                >= view.computeVerticalScrollRange();
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