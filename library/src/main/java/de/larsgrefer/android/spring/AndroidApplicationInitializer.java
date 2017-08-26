package de.larsgrefer.android.spring;

import android.app.Application;
import com.googlecode.openbeans.Introspector;
import de.larsgrefer.android.spring.core.io.AndroidResourceLoader;
import de.larsgrefer.android.spring.core.io.support.AndroidPathMatchingResourcePatternResolver;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotationConfigRegistry;
import org.springframework.context.support.GenericApplicationContext;

@AllArgsConstructor
public class AndroidApplicationInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

    private Application application;

    @Override
    public void initialize(GenericApplicationContext applicationContext) {

        applicationContext.setId(application.getPackageName());
        applicationContext.setDisplayName(application.getApplicationInfo().loadLabel(application.getPackageManager()).toString());

        applicationContext.getDefaultListableBeanFactory().registerSingleton("application", application);

        if (applicationContext instanceof AnnotationConfigRegistry) {
            applicationContext.registerAlias("application", Introspector.decapitalize(application.getClass().getSimpleName()));
            ((AnnotationConfigRegistry) applicationContext).register(application.getClass());
        }

        applicationContext.setResourceLoader(new AndroidPathMatchingResourcePatternResolver(new AndroidResourceLoader(application.getClassLoader())));
    }
}
