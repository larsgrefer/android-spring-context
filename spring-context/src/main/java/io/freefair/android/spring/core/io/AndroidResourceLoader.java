package io.freefair.android.spring.core.io;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;


/**
 * @author Lars Grefer
 */
public class AndroidResourceLoader extends DefaultResourceLoader {

    @Override
    public Resource getResource(String location) {

        if (location.startsWith(CLASSPATH_URL_PREFIX) && location.endsWith(".class")) {

            String path = location.substring(CLASSPATH_URL_PREFIX.length(), location.length() - 6);
            String className = ClassUtils.convertResourcePathToClassName(path);

            return new DexClassResource(className, getClassLoader());

        } else {
            return super.getResource(location);
        }
    }
}
