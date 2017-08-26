package de.larsgrefer.android.spring.integration;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import de.larsgrefer.android.spring.context.ApplicationContextProvider;
import de.larsgrefer.android.spring.delegate.SpringActivityDelegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author Lars Grefer
 */
public abstract class SpringActivity extends Activity implements ApplicationContextProvider {

    private final SpringActivityDelegate delegate = new SpringActivityDelegate(this);

    @Override
    @MainThread
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
    }

    @Override
    public GenericApplicationContext getSpringApplicationContext() {
        return delegate.getSpringApplicationContext();
    }
}
