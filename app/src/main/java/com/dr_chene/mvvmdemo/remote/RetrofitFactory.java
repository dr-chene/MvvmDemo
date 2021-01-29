package com.dr_chene.mvvmdemo.remote;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//命名不太规范，别学
public class RetrofitFactory {

    private static final Map<String, Retrofit> retrofitMap = new HashMap<>();
    public static final String NORMAL = "normal";

    private RetrofitFactory() {
    }

    public static Retrofit create(String type) {
        if (!retrofitMap.containsKey(type)) {
            switch (type) {
                case NORMAL:
                    retrofitMap.put(type, normal());
                    break;
                default:

            }
        }
        return retrofitMap.get(type);
    }

    private static Retrofit normal() {
        return new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }
}
