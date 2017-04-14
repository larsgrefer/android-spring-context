package io.freefair.android.spring.delegate;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.springframework.util.StopWatch;

import io.freefair.android.spring.configuration.AndroidScopeConfiguration;
import io.freefair.android.spring.context.AndroidApplicationContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lars Grefer
 */
@Slf4j
public class SpringApplicationDelegate extends AbstractSpringDelegate<Application> {

    private final Class<?> buildConfigClass;

    private AndroidApplicationContext applicationContext;

    public SpringApplicationDelegate(@NonNull Application application, @Nullable Class<?> buildConfigClass) {
        super(application);
        this.buildConfigClass = buildConfigClass;
    }

    public void onCreate() {
        StopWatch stopWatch = new StopWatch("AndroidApplicationContext");
        stopWatch.start("construct");
        applicationContext = new AndroidApplicationContext(element, buildConfigClass);

        applicationContext.register(AndroidScopeConfiguration.class);

        stopWatch.stop();

        stopWatch.start("refresh");
        applicationContext.refresh();
        stopWatch.stop();

        log.info("Started {} in {} seconds", applicationContext.getDisplayName(), stopWatch.getTotalTimeSeconds());
        log.debug(stopWatch.prettyPrint());
    }

    public AndroidApplicationContext getSpringContext() {
        return applicationContext;
    }

    @Override
    protected Application getApplication() {
        return element;
    }

    @Override
    public AndroidApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void onTerminate() {
        applicationContext.close();
        applicationContext = null;
    }
}
