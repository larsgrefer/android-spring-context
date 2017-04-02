package io.freefair.android.spring.integration;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import io.freefair.android.spring.delegate.SpringActivityDelegate;

/**
 * @author Lars Grefer
 */
public class SpringActivity extends Activity {

    private final SpringActivityDelegate delegate = new SpringActivityDelegate(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
    }
}
