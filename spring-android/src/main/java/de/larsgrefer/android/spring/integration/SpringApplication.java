package de.larsgrefer.android.spring.integration;

import android.app.Application;
import android.support.annotation.CallSuper;
import de.larsgrefer.android.spring.context.ApplicationContextProvider;
import de.larsgrefer.android.spring.delegate.SpringApplicationDelegate;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author Lars Grefer
 */
public abstract class SpringApplication extends Application implements ApplicationContextProvider {

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

    @Override
    public GenericApplicationContext getSpringApplicationContext() {
        return delegate.getSpringApplicationContext();
    }
}
