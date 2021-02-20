package com.dr_chene.mvvmdemo.view;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.dr_chene.mvvmdemo.NetworkChangeReceiver;

public abstract class BaseActivity<T extends ViewDataBinding>
        extends AppCompatActivity implements IViewInit<T> {

    private T binding;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getContentViewResId());
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

        initView(binding);
        initAction(binding);
        subscribe(binding);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public T getBinding() {
        return binding;
    }
}
