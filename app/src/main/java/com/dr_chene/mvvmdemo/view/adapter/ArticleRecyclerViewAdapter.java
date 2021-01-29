package com.dr_chene.mvvmdemo.view.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.dr_chene.mvvmdemo.bean.Article;
import com.dr_chene.mvvmdemo.databinding.RecycleItemArticleBinding;

//ArticleRecyclerView的适配器，不用太在意
public class ArticleRecyclerViewAdapter extends ListAdapter<Article, RecyclerView.ViewHolder> {

    public ArticleRecyclerViewAdapter() {
        super(new DiffUtil.ItemCallback<Article>() {
            @Override
            public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
                return oldItem.getTitle().equals(newItem.getTitle());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
                return oldItem.getLink().equals(newItem.getLink());
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleViewHolder(
                RecycleItemArticleBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ArticleViewHolder) holder).bind(getItem(position));
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final RecycleItemArticleBinding binding;

        public ArticleViewHolder(RecycleItemArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Article article) {
            binding.setArticle(article);
            if (article.isCollect()) {
                binding.ivArticleCollect.setSelected(true);
            }
            binding.executePendingBindings();
        }
    }
}
