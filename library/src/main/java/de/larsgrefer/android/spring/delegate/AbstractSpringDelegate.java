package de.larsgrefer.android.spring.delegate;

import android.app.Application;
import android.support.annotation.NonNull;
import de.larsgrefer.android.spring.context.ApplicationContextProvider;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.Assert;

@Slf4j
abstract class AbstractSpringDelegate<T> implements ApplicationContextProvider {

    @Getter
    private final T element;

    AbstractSpringDelegate(@NonNull T element) {
        Assert.notNull(element, "element must not be null");
        this.element = element;
    }

    public GenericApplicationContext getSpringApplicationContext() {
        Application application = getApplication();
        if (application instanceof ApplicationContextProvider) {
            return ((ApplicationContextProvider) application).getSpringApplicationContext();
        }
        return null;
    }

    protected abstract Application getApplication();

    void autowireElement() {
        log.info("autowire {}", element);
        getSpringApplicationContext().getAutowireCapableBeanFactory().autowireBean(element);
    }

}
