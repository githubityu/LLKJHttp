package com.llkj.llkjhttp.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by yujunlong on 2016/11/11.
 */

public class LlkjApplication extends Application {
    private static LlkjApplication instance;
    public static Handler sHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        instance =this;
    }

    public static Application context() {
        return  instance;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
