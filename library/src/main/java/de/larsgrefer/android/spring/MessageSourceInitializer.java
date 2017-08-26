package de.larsgrefer.android.spring;

import de.larsgrefer.android.spring.context.AndroidResourcesMessageSource;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigRegistry;
import org.springframework.context.support.GenericApplicationContext;

public class MessageSourceInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

    @Override
    public void initialize(GenericApplicationContext applicationContext) {

        if(applicationContext instanceof AnnotationConfigRegistry) {
            ((AnnotationConfigRegistry) applicationContext).register(AndroidResourcesMessageSource.class);
        } else {
            new AnnotatedBeanDefinitionReader(applicationContext).registerBean(AndroidResourcesMessageSource.class);
        }
    }
}
