package io.freefair.android.spring.delegate;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.springframework.context.support.GenericApplicationContext;

import io.freefair.android.spring.beans.factory.config.ActivityScope;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lars Grefer
 */
@Slf4j
public class SpringActivityDelegate extends AbstractSpringDelegate<Activity> {

    public SpringActivityDelegate(@NonNull Activity activity) {
        super(activity);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        getActivityScope(getApplicationContext()).onCreateActivity(element);
        autowireElement();
    }

    public void onDestroy() {
        log.info("onDestroy: {}", element);
        getActivityScope().onDestroyActivity(element);
    }

    protected ActivityScope getActivityScope() {
        return getActivityScope(getApplicationContext());

    }

    protected ActivityScope getActivityScope(GenericApplicationContext applicationContext) {
        return (ActivityScope) applicationContext.getDefaultListableBeanFactory()
                .getRegisteredScope("activity");
    }

    @Override
    protected Application getApplication() {
        return element.getApplication();
    }
}
