package io.freefair.android.spring.core.io;

import org.springframework.core.io.AbstractResource;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by larsgrefer on 02.04.17.
 */
@RequiredArgsConstructor
@Getter
public class DexClassResource extends AbstractResource {

    private final String className;
    private final ClassLoader classLoader;

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
        try {
            classLoader.loadClass(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
