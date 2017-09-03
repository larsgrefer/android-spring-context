package de.larsgrefer.android.spring.core.io;

import android.support.annotation.NonNull;
import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;
import lombok.Getter;
import org.springframework.core.io.AbstractResource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

/**
 * A {@link org.springframework.core.io.Resource resource} representing a path (or package) inside
 * a {@link DexFile}
 *
 * @author Lars Grefer
 * @see de.larsgrefer.android.spring.core.io.support.AndroidPathMatchingResourcePatternResolver
 */
@Getter
public class DexPathResource extends AbstractResource {

    protected final PathClassLoader classLoader;
    protected final DexFile dexFile;
    protected final String path;
    private Boolean exists;

    public DexPathResource(PathClassLoader classLoader, DexFile dexFile, String path) {
        Assert.notNull(classLoader, "the classLoader must not be null");
        Assert.notNull(dexFile, "the dexFile must not be null");
        Assert.notNull(path, "the path must not be null");

        this.classLoader = classLoader;
        this.dexFile = dexFile;
        this.path = path;
    }

    @Override
    @NonNull
    public String getDescription() {
        return "dexPath " + path;
    }

    /**
     * @return false
     */
    @Override
    public boolean isReadable() {
        return false;
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
    public boolean exists() {
        if (exists == null) {
            exists = checkExists();
        }
        return exists;
    }

    private boolean checkExists() {
        Enumeration<String> entries = dexFile.entries();
        String pkg = path.replace('/', '.');
        while (entries.hasMoreElements()) {
            String entry = entries.nextElement();
            if (entry.startsWith(pkg)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @NonNull
    public URL getURL() throws IOException {
        return new URL("file", dexFile.getName() + "!", path);
    }
}
