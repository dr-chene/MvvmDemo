package com.dr_chene.mvvmdemo.view;

import androidx.databinding.ViewDataBinding;

public interface IViewInit<T extends ViewDataBinding> {
    //初始化视图，做一些设置，例如设置toolbar等
    void initView(T binding);

    //初始化事件，主要做事件的监听的初始化
    void initAction(T binding);

    //订阅viewmodel的livedata事件
    //可选择性继承
    default void subscribe(T binding) {
    }

    T getBinding();

    //返回xml的资源id用于加载视图
    int getContentViewResId();
}
