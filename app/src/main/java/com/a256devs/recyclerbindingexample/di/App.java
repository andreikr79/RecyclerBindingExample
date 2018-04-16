package com.a256devs.recyclerbindingexample.di;

import android.app.Application;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;

public class App extends Application {

    private static MainComponent mainComponent;

    private static Application application;

    public static MainComponent getMainComponent() {
        return mainComponent;
    }

    public static Application getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        mainComponent = DaggerMainComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static String getResString(int resId) {
        if (null == application) {
            return "";
        }
        return application.getString(resId);
    }

    public static String[] getResStringArray(int resId) {
        if (null == application) {
            return null;
        }
        return application.getResources().getStringArray(resId);
    }

    public static String getResString(int resId, Object... formatArgs) {
        if (null == application) {
            return "";
        }
        return application.getString(resId, formatArgs);
    }
    public static int getResColor(int resId) {
        if (null == application) {
            return -1;
        }
        return ContextCompat.getColor(application, resId);
    }

    public static float getResDimension(int resId) {
        if (null == application) {
            return -1;
        }
        return application.getResources().getDimension(resId);
    }
    public static Typeface getResFont(int resId) {
        if (null == application) {
            return null;
        }
        return ResourcesCompat.getFont(application, resId);
    }

    public static Drawable getResDrawable(int resId) {
        if (null == application) {
            return null;
        }
        return ContextCompat.getDrawable(application, resId);
    }
}
