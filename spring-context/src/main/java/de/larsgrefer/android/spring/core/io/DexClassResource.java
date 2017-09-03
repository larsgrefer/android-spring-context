package de.larsgrefer.android.spring.core.io;

import android.support.annotation.NonNull;
import dalvik.system.DexFile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.AbstractResource;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * A {@link org.springframework.core.io.Resource resource} representing a {@link Class} inside
 * a {@link DexFile}.
 * <p>
 * The {@link #getFilename()} and {@link #getURL()} methods emulate a class file.
 *
 * @author Lars Grefer
 * @see AndroidResourceLoader
 * @see de.larsgrefer.android.spring.core.io.support.AndroidPathMatchingResourcePatternResolver
 * @see de.larsgrefer.android.spring.core.type.classreading.AndroidMetadataReader
 */
@RequiredArgsConstructor
@Getter
public class DexClassResource extends AbstractResource {

    private final String className;
    private final ClassLoader classLoader;
    private Class<?> clazz = null;
    private Boolean exists = null;

    @Override
    @NonNull
    public String getDescription() {
        return "class " + className;
    }

    /**
     * @return true, if the represented class can be loaded
     */
    @Override
    public boolean isReadable() {
        return exists();
    }

    /**
     * @return nothing
     * @throws IOException always
     */
    @Override
    @NonNull
    public InputStream getInputStream() throws IOException {
        throw new IOException();
    }

    @Override
    @NonNull
    public String getFilename() {
        return ClassUtils.getShortName(className) + ".class";
    }

    @Override
    @NonNull
    public URL getURL() throws IOException {
        String resourcePath = ClassUtils.convertClassNameToResourcePath(className) + ".class";
        return new URL("file", null, resourcePath);
    }

    @Override
    public boolean exists() {
        if (exists == null) {
            return exists = checkExists();
        }
        return exists;
    }

    private boolean checkExists() {
        if (clazz != null) {
            return true;
        }
        try {
            loadClass();
            exists = true;
        } catch (ClassNotFoundException e) {
            exists = false;
        }
        return false;
    }

    @NonNull
    public Class<?> loadClass() throws ClassNotFoundException {
        if (clazz == null) {
            clazz = classLoader.loadClass(className);
        }
        return clazz;
    }
}
