package io.freefair.android.spring.integration;

import android.app.Application;

import io.freefair.android.spring.context.AndroidApplicationContext;
import io.freefair.android.spring.delegate.SpringApplicationDelegate;

/**
 * @author Lars Grefer
 */
public class SpringApplication extends Application {

    private final SpringApplicationDelegate delegate = new SpringApplicationDelegate(this);

    @Override
    public void onCreate() {
        super.onCreate();
        delegate.onCreate();
    }

    public AndroidApplicationContext getSpringContext() {
        return delegate.getSpringContext();
    }
}
