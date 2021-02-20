package com.dr_chene.mvvmdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * created by dr_chene on 2021/2/19
 * desc
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    public static final String TAG = "NetworkChangeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected && !App.connected) {
            Toast.makeText(context, "网络可用", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onReceive: 网络可用");
            App.connected = true;
        } else if (!isConnected && App.connected) {
            Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onReceive: 网络不可用");
            App.connected = false;
        }
    }

}
