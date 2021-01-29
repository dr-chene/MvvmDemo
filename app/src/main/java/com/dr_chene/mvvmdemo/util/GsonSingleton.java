package com.dr_chene.mvvmdemo.util;

import com.google.gson.Gson;

//gson单例
public class GsonSingleton {
    private volatile static Gson instance;

    private GsonSingleton() {
    }

    public static Gson getInstance() {
        if (instance == null) {
            synchronized (GsonSingleton.class) {
                if (instance == null) {
                    instance = new Gson();
                }
            }
        }
        return instance;
    }
}
