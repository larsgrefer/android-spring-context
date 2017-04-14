package io.freefair.android.spring.core.env;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Lars Grefer
 */
@Slf4j
public class BuildConfigPropertySource extends SystemEnvironmentPropertySource {

    public BuildConfigPropertySource(@NonNull String name, @NonNull Class<?> buildConfigClass) {
        super(name, convertToMap(buildConfigClass));
    }

    static Map<String, Object> convertToMap(@Nullable Class<?> buildConfigClass) {
        if(buildConfigClass == null) {
            return null;
        }
        final Map<String, Object> map = new HashMap<>();

        ReflectionUtils.doWithFields(buildConfigClass, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                if (ReflectionUtils.isPublicStaticFinal(field)) {
                    map.put(field.getName(), field.get(null));
                } else {
                    log.info("Ignoring non public static final field {}", field.getName());
                }
            }
        });

        return map;
    }
}
