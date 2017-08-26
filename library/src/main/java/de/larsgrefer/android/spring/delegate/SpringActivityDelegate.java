package de.larsgrefer.android.spring.delegate;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lars Grefer
 */
@Slf4j
public class SpringActivityDelegate extends AbstractSpringDelegate<Activity> {

    public SpringActivityDelegate(@NonNull Activity activity) {
        super(activity);
    }

    @MainThread
    public void onCreate(@Nullable Bundle savedInstanceState) {
        autowireElement();
    }

    @Override
    protected Application getApplication() {
        return getElement().getApplication();
    }

}
