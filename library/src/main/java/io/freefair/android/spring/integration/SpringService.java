package io.freefair.android.spring.integration;

import android.app.Service;

import io.freefair.android.spring.delegate.SpringServiceDelegate;

/**
 * @author Lars Grefer
 */
public abstract class SpringService extends Service {

    private final SpringServiceDelegate delegate = new SpringServiceDelegate(this);

    @Override
    public void onCreate() {
        super.onCreate();
        delegate.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        delegate.onDestroy();
    }
}
