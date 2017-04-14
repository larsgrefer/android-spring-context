package io.freefair.android.spring.core.type.classreading;

import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;

import io.freefair.android.spring.core.io.DexClassResource;

/**
 * @author Lars Grefer
 */
public class AndroidMetadataReader implements MetadataReader {

    private final DexClassResource resource;
    private final StandardAnnotationMetadata standardAnnotationMetadata;

    public AndroidMetadataReader(DexClassResource resource, ClassLoader classLoader) {

        this.resource = resource;
        try {
            Class<?> aClass = classLoader.loadClass(resource.getClassName());
            standardAnnotationMetadata = new StandardAnnotationMetadata(aClass);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public DexClassResource getResource() {
        return resource;
    }

    @Override
    public ClassMetadata getClassMetadata() {
        return standardAnnotationMetadata;
    }

    @Override
    public AnnotationMetadata getAnnotationMetadata() {
        return standardAnnotationMetadata;
    }
}
