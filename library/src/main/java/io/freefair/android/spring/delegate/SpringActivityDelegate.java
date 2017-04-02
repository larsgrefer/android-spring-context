package io.freefair.android.spring.delegate;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.springframework.context.support.AbstractApplicationContext;

import io.freefair.android.spring.context.ContextHolder;
import lombok.RequiredArgsConstructor;

/**
 * @author Lars Grefer
 */
@RequiredArgsConstructor
public class SpringActivityDelegate {

    private final Activity activity;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        Application application = activity.getApplication();

        AbstractApplicationContext context = ContextHolder.getContext(application);

        context.getAutowireCapableBeanFactory().autowireBean(activity);
    }
}
