package io.freefair.android.spring.integration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.freefair.android.spring.delegate.SpringActivityDelegate;


/**
 * @author Lars Grefer
 */
public class SpringAppCompatActivity extends AppCompatActivity {

    private final SpringActivityDelegate delegate = new SpringActivityDelegate(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
    }
}
