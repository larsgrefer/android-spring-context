package de.larsgrefer.android.spring.core.io;

import org.springframework.core.io.AbstractResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Lars Grefer
 */
@Getter
@AllArgsConstructor
public class DexPathResource extends AbstractResource {

    protected final PathClassLoader classLoader;
    protected final DexFile dexFile;
    protected final String path;

    @Override
    public String getDescription() {
        return "dexPath " + path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public boolean exists() {
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
    public URL getURL() throws IOException {
        return new URL("file", dexFile.getName() + "!", path);
    }
}
