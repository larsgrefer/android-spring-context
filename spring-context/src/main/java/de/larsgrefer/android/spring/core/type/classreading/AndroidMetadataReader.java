package de.larsgrefer.android.spring.core.type.classreading;

import android.support.annotation.NonNull;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;

import de.larsgrefer.android.spring.core.io.DexClassResource;

/**
 * @author Lars Grefer
 * @see DexClassResource
 */
public class AndroidMetadataReader implements MetadataReader {

    @NonNull
    private final DexClassResource resource;
    @NonNull
    private final StandardAnnotationMetadata standardAnnotationMetadata;

    public AndroidMetadataReader(@NonNull DexClassResource resource, ClassLoader classLoader) {

        this.resource = resource;
        try {
            Class<?> clazz;
            if (classLoader == resource.getClassLoader()) {
                clazz = resource.loadClass();
            } else {
                clazz = classLoader.loadClass(resource.getClassName());
            }
            standardAnnotationMetadata = new StandardAnnotationMetadata(clazz);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    @NonNull
    public DexClassResource getResource() {
        return resource;
    }

    @Override
    @NonNull
    public ClassMetadata getClassMetadata() {
        return standardAnnotationMetadata;
    }

    @Override
    @NonNull
    public AnnotationMetadata getAnnotationMetadata() {
        return standardAnnotationMetadata;
    }
}
