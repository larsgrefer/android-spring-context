package de.larsgrefer.android.spring.demo;

import org.slf4j.impl.HandroidLoggerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import de.larsgrefer.android.spring.integration.SpringApplication;

/**
 * @author Lars Grefer
 */
@Configuration
@ComponentScan
public class DemoApplication extends SpringApplication {

    static DemoApplication me;

    public DemoApplication() {
        super(BuildConfig.class);
    }

    @Override
    public void onCreate() {
        HandroidLoggerAdapter.DEBUG = BuildConfig.DEBUG;
        super.onCreate();
    }

    @Bean
    public Dummy dummy() {
        boolean test = me == this;

        return new Dummy();
    }
}
