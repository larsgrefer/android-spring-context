package de.larsgrefer.android.spring.context;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Bundle;
import de.larsgrefer.android.spring.core.env.BuildConfigPropertySource;
import de.larsgrefer.android.spring.core.env.BundlePropertySource;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@AllArgsConstructor
public class AndroidEnvironmentInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final Application application;

    private final Class<?> buildConfigClass;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        try {
            Bundle metaData = application.getPackageManager().getApplicationInfo(application.getPackageName(), PackageManager.GET_META_DATA).metaData;
            environment.getPropertySources().addLast(new BundlePropertySource("applicationInfoMetaData", metaData));
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (buildConfigClass != null) {
            environment.getPropertySources().addLast(new BuildConfigPropertySource("buildConfig", buildConfigClass));
        }
    }
}
