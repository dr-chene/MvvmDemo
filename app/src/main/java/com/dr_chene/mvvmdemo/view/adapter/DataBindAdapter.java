package com.dr_chene.mvvmdemo.view.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.dr_chene.mvvmdemo.bean.Article;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataBindAdapter {

    private DataBindAdapter() {
    }

    @BindingAdapter("bindAuthor")
    public static void bindText(TextView view, Article article) {
        String text = "";
        if (article.getAuthor() != null && !article.getAuthor().isEmpty()) {
            text = article.getAuthor();
        } else if (article.getShareUser() != null && !article.getShareUser().isEmpty()) {
            text = article.getShareUser();
        }
        view.setText(text);
    }

    @BindingAdapter("bindTime")
    public static void bindTime(TextView view, Long time) {
        if (time == 0) {
            view.setVisibility(View.INVISIBLE);
            return;
        }
        long duration = System.currentTimeMillis() - time;
        String text;
        if (duration < 60) text = "刚才";
        else if (duration < 3600) text = duration / 60000 + "分钟前";
        else if (duration < 86400) text = duration / 3600000 + "小时前";
        else if (duration < 259200) text = duration / 86400000 + "天前";
        else {
            Date date = new Date(time);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            text = format.format(date);
        }
        view.setText(text);
    }
}
