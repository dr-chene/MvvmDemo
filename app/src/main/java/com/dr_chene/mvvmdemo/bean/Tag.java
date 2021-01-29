package com.dr_chene.mvvmdemo.bean;

import androidx.core.content.ContextCompat;

import com.dr_chene.mvvmdemo.App;
import com.dr_chene.mvvmdemo.R;

import java.util.Random;

public class Tag {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private final int color;

    public int getColor() {
        return color;
    }

    public Tag() {
        color = colors[new Random().nextInt(colors.length)];
    }

    private static final int[] colors = {
            R.color.color_blue,
            R.color.color_blue_light,
            R.color.color_blue_lighter,
            R.color.color_blue_dark,
            R.color.color_cyan,
            R.color.color_red,
            R.color.color_red_dark,
            R.color.color_pink,
            R.color.color_purple,
            R.color.color_purple_dark,
            R.color.color_origin,
            R.color.color_origin_dark,
            R.color.color_yellow_dark,
            R.color.color_yellow_darker,
            R.color.color_green,
            R.color.color_green_light,
            R.color.color_green_dark,
            R.color.color_green_darker,
            R.color.color_brown,
            R.color.color_brown_light
    };

    private int getResColor(int colorRes) {
        return ContextCompat.getColor(App.getContext(), colorRes);
    }
}
