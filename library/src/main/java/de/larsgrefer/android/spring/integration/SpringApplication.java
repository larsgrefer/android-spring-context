package de.larsgrefer.android.spring.integration;

import android.app.Application;
import android.support.annotation.CallSuper;

import de.larsgrefer.android.spring.context.AndroidApplicationContext;
import de.larsgrefer.android.spring.delegate.SpringApplicationDelegate;

/**
 * @author Lars Grefer
 */
public class SpringApplication extends Application {

    private final SpringApplicationDelegate delegate;

    public SpringApplication() {
        this(null);
    }

    public SpringApplication(Class<?> buildConfigClass) {
        delegate = new SpringApplicationDelegate(this, buildConfigClass);
    }

    @Override
    @CallSuper
    public void onCreate() {
        super.onCreate();
        delegate.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        delegate.onTerminate();
    }

    public AndroidApplicationContext getSpringContext() {
        return delegate.getSpringContext();
    }
}
