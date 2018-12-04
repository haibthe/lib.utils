package com.hb.lib.app;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import timber.log.Timber;

public class AppLifecycleHandler implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

    private LifecycleDelegate mLifecycleDelegate;
    private boolean appInForeground = false;

    public AppLifecycleHandler(@NonNull LifecycleDelegate lifecycleDelegate) {
        mLifecycleDelegate = lifecycleDelegate;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Timber.d("onActivityCreated: %s", activity.getClass().getName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Timber.d("onActivityStarted: %s", activity.getClass().getName());
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Timber.d("onActivityResumed: %s", activity.getClass().getName());
        if (!appInForeground) {
            appInForeground = true;
            mLifecycleDelegate.onAppForegrounded();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Timber.d("onActivityPaused: %s", activity.getClass().getName());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Timber.d("onActivityStopped: %s", activity.getClass().getName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Timber.d("onActivitySaveInstanceState: %s", activity.getClass().getName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Timber.d("onActivityDestroyed: %s", activity.getClass().getName());
    }

    @Override
    public void onTrimMemory(int level) {
        Timber.d("onTrimMemory: %s", "" + level);
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            appInForeground = false;
            mLifecycleDelegate.onAppBackgrounded();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Timber.d("onConfigurationChanged");
    }

    @Override
    public void onLowMemory() {
        Timber.d("onLowMemory");
    }
}
