package de.larsgrefer.android.spring.delegate;

import android.app.Application;
import android.support.annotation.NonNull;
import de.larsgrefer.android.spring.context.ContextHolder;
import de.larsgrefer.android.spring.integration.SpringApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.Assert;

@Slf4j
abstract class AbstractSpringDelegate<T> {

    final T element;

    AbstractSpringDelegate(@NonNull T element) {
        Assert.notNull(element, "element must not be null");
        this.element = element;
    }

    GenericApplicationContext getApplicationContext() {
        Application application = getApplication();
        if (application instanceof SpringApplication) {
            return ((SpringApplication) application).getSpringContext();
        } else {
            return ContextHolder.getContext(application);
        }
    }

    protected abstract Application getApplication();

    void autowireElement() {
        log.info("autowire {}", element);
        getApplicationContext().getAutowireCapableBeanFactory().autowireBean(element);
    }


}
