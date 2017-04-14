package io.freefair.android.spring.delegate;

import android.app.Application;
import android.support.annotation.NonNull;

import org.springframework.util.Assert;

import io.freefair.android.spring.context.AndroidApplicationContext;
import io.freefair.android.spring.context.ContextHolder;
import io.freefair.android.spring.integration.SpringApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract class AbstractSpringDelegate<T> {

    final T element;

    AbstractSpringDelegate(@NonNull T element) {
        Assert.notNull(element, "element must not be null");
        this.element = element;
    }

    AndroidApplicationContext getApplicationContext() {
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