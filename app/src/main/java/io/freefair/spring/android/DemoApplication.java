package io.freefair.spring.android;

import org.slf4j.impl.HandroidLoggerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.freefair.android.spring.integration.SpringApplication;

/**
 * Created by larsgrefer on 01.04.17.
 */
@Configuration
@ComponentScan("io.freefair.spring.android")
public class DemoApplication extends SpringApplication {

    static DemoApplication me;

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
