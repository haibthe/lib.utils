package com.hb.lib.app;

import android.app.Activity;

public interface OnActivityCurrentListener {

    void setCurrentActivity(Activity activity);

    Activity getCurrentActivity();
}