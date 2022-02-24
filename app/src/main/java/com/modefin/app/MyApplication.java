package com.modefin.app;

import android.app.Application;

import com.modefin.app.model.MakeupResponse;

import lombok.Getter;
import lombok.Setter;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Getter
    @Setter
    public MakeupResponse makeupResponse;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        if (mInstance == null) {
            mInstance = new MyApplication();
        }
        return mInstance;
    }
}
