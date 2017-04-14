package io.freefair.android.spring.integration;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import io.freefair.android.spring.delegate.SpringActivityDelegate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lars Grefer
 */
@Slf4j
public class SpringActivity extends Activity {

    private final SpringActivityDelegate delegate = new SpringActivityDelegate(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        log.info("onCreate: {}", this);
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
    }
}
