package de.larsgrefer.android.spring.integration;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;

import de.larsgrefer.android.spring.delegate.SpringActivityDelegate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lars Grefer
 */
@Slf4j
public class SpringActivity extends Activity {

    private final SpringActivityDelegate delegate = new SpringActivityDelegate(this);

    @Override
    @MainThread
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
    }

}
