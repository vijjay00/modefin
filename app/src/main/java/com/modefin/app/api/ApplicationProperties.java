package com.modefin.app.api;

import android.content.Context;

import com.modefin.app.FuncsVars;
import com.modefin.app.MyApplication;
import com.modefin.app.R;

import java.util.Properties;

public class ApplicationProperties extends Properties {

    private static final long serialVersionUID = 1L;

    private static ApplicationProperties instance;

    private ApplicationProperties() {
        super();
    }

    public static ApplicationProperties getInstance() {
        if (instance == null) {
            instance = new ApplicationProperties();
            instance.loadProperties();
        }
        return instance;
    }

    private void loadProperties() {
        loadProperties(R.raw.app);
    }

    private void loadProperties(int resourceId) {
        FuncsVars.loadProperties(resourceId,this);
    }

    public static Context getContext() {
        return MyApplication.getInstance();
    }
}

