package de.larsgrefer.android.spring.delegate;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import de.larsgrefer.android.spring.context.AndroidApplicationInitializer;
import de.larsgrefer.android.spring.context.AndroidEnvironmentInitializer;
import de.larsgrefer.android.spring.context.ContextHolderInitializer;
import de.larsgrefer.android.spring.context.MessageSourceInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lars Grefer
 */
@Slf4j
public class SpringApplicationDelegate extends AbstractSpringDelegate<Application> {

    private final List<ApplicationContextInitializer<? super AnnotationConfigApplicationContext>> contextInitializers = new ArrayList<>();

    private AnnotationConfigApplicationContext applicationContext;

    public SpringApplicationDelegate(@NonNull Application application, @Nullable Class<?> buildConfigClass) {
        super(application);

        contextInitializers.add(new AndroidEnvironmentInitializer(application, buildConfigClass));
        contextInitializers.add(new AndroidApplicationInitializer(application));
        contextInitializers.add(new ContextHolderInitializer(application));
        contextInitializers.add(new MessageSourceInitializer());
    }

    public void onCreate() {
        StopWatch stopWatch = new StopWatch("AndroidApplicationContext");
        stopWatch.start("construct");
        applicationContext = new AnnotationConfigApplicationContext();

        AnnotationAwareOrderComparator.sort(contextInitializers);

        for (ApplicationContextInitializer<? super AnnotationConfigApplicationContext> contextInitializer : contextInitializers) {
            contextInitializer.initialize(applicationContext);
        }

        applicationContext.register(element.getClass());

        stopWatch.stop();

        stopWatch.start("refresh");
        applicationContext.refresh();
        stopWatch.stop();

        log.info("Started {} in {} seconds", applicationContext.getDisplayName(), stopWatch.getTotalTimeSeconds());
        log.debug(stopWatch.prettyPrint());
    }

    public GenericApplicationContext getSpringContext() {
        return applicationContext;
    }

    @Override
    protected Application getApplication() {
        return element;
    }

    @Override
    public GenericApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void onTerminate() {
        applicationContext.close();
        applicationContext = null;
    }
}
