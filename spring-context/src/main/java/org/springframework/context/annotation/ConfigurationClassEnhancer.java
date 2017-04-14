package org.springframework.context.annotation;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Lars Grefer
 */
@SuppressWarnings("unused")
@Slf4j
class ConfigurationClassEnhancer {

    ConfigurationClassEnhancer() {
    }

    public Class<?> enhance(Class<?> configClass, ClassLoader classLoader) {
        log.warn("Not enhanching configuration {}", configClass);
        return configClass;
    }
}
