package org.springframework.core.type.classreading;

import org.springframework.asm.ClassReader;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import io.freefair.android.spring.core.io.DexClassResource;

/**
 * {@link MetadataReader} implementation based on an ASM
 * {@link org.springframework.asm.ClassReader}.
 *
 * <p>Package-visible in order to allow for repackaging the ASM library
 * without effect on users of the {@code core.type} package.
 *
 * @author Juergen Hoeller
 * @author Costin Leau
 * @author Lars Grefer
 * @since 2.5
 */
final class SimpleMetadataReader implements MetadataReader {

    private final Resource resource;

    private final ClassMetadata classMetadata;

    private final AnnotationMetadata annotationMetadata;

    SimpleMetadataReader(Resource resource, ClassLoader classLoader) throws IOException {

        if (resource instanceof DexClassResource) {
            try {
                Class<?> aClass = classLoader.loadClass(((DexClassResource) resource).getClassName());
                StandardAnnotationMetadata standardAnnotationMetadata = new StandardAnnotationMetadata(aClass);
                this.annotationMetadata = standardAnnotationMetadata;
                this.classMetadata = standardAnnotationMetadata;
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException(e);
            }
        } else  {

            InputStream is = new BufferedInputStream(resource.getInputStream());
            ClassReader classReader;
            try {
                classReader = new ClassReader(is);
            } catch (IllegalArgumentException ex) {
                throw new NestedIOException("ASM ClassReader failed to parse class file - " +
                        "probably due to a new Java class file version that isn't supported yet: " + resource, ex);
            } finally {
                is.close();
            }

            AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor(classLoader);
            classReader.accept(visitor, ClassReader.SKIP_DEBUG);

            this.annotationMetadata = visitor;
            // (since AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor)
            this.classMetadata = visitor;
        }
        this.resource = resource;
    }


    @Override
    public Resource getResource() {
        return this.resource;
    }

    @Override
    public ClassMetadata getClassMetadata() {
        return this.classMetadata;
    }

    @Override
    public AnnotationMetadata getAnnotationMetadata() {
        return this.annotationMetadata;
    }

}
