package io.freefair.android.spring.core.io;

import org.springframework.core.io.AbstractResource;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Lars Grefer
 */
@RequiredArgsConstructor
@Getter
public class DexClassResource extends AbstractResource {

    private final String className;
    private final ClassLoader classLoader;
    private Class<?> clazz = null;
    private Boolean exists = null;

    public DexClassResource(Class<?> clazz) {
        this(clazz.getName(), clazz.getClassLoader());
    }

    @Override
    public String getDescription() {
        return "class " + className;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getFilename() {
        return ClassUtils.getShortName(className) + ".class";
    }

    @Override
    public URL getURL() throws IOException {
        String resourcePath = ClassUtils.convertClassNameToResourcePath(className) + ".class";
        return new URL("file", null, resourcePath);
    }

    @Override
    public boolean exists() {
        if (exists == null) {
            if (clazz != null) {
                return exists = true;
            }
            try {
                loadClass();
                exists = true;
            } catch (ClassNotFoundException e) {
                exists = false;
            }
        }
        return exists;
    }

    public Class<?> loadClass() throws ClassNotFoundException {
        if(clazz == null) {
            clazz = classLoader.loadClass(className);
        }
        return clazz;
    }
}
