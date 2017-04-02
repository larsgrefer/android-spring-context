package io.freefair.android.spring.delegate;

import android.app.Application;

import org.springframework.util.StopWatch;

import io.freefair.android.spring.context.AndroidApplicationContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lars Grefer
 */
@Slf4j
@RequiredArgsConstructor
public class SpringApplicationDelegate {

    private final Application application;

    private AndroidApplicationContext applicationContext;

    public void onCreate() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        applicationContext = new AndroidApplicationContext(application);

        applicationContext.refresh();
        stopWatch.stop();
        log.info("Started {} in {} seconds", applicationContext.getDisplayName(), stopWatch.getTotalTimeSeconds());
    }

    public AndroidApplicationContext getSpringContext() {
        return applicationContext;
    }

}
