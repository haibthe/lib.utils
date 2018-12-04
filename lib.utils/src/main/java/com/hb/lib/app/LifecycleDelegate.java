package com.hb.lib.app;

public interface LifecycleDelegate {

    void onAppBackgrounded();

    void onAppForegrounded();

    void onAppKilled();
}
