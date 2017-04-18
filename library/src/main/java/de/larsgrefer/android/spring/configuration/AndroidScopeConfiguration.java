package de.larsgrefer.android.spring.configuration;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.larsgrefer.android.spring.beans.factory.config.ActivityScope;


/**
 * @author Lars Grefer
 */
@Configuration
public class AndroidScopeConfiguration {

    @Bean
    public static CustomScopeConfigurer androidScopeConfigurer() {
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();

        customScopeConfigurer.addScope("activity", new ActivityScope());

        return customScopeConfigurer;
    }
}
