package de.larsgrefer.android.spring.context;

import android.app.Application;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.Ordered;

@AllArgsConstructor
public class ContextHolderInitializer implements ApplicationContextInitializer<GenericApplicationContext>, Ordered {

    private Application application;

    @Override
    public void initialize(GenericApplicationContext applicationContext) {
        ContextHolder.registerContext(application, applicationContext);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
