package org.springframework.core.type.classreading;

import org.springframework.core.io.Resource;

import java.io.IOException;

import io.freefair.android.spring.core.io.DexClassResource;
import io.freefair.android.spring.core.type.classreading.AndroidMetadataReader;

/**
 * @author Lars Grefer
 */
@SuppressWarnings("unused")
final class SimpleMetadataReader extends AndroidMetadataReader {

    SimpleMetadataReader(Resource resource, ClassLoader classLoader) throws IOException {
        super((DexClassResource) resource, classLoader);
    }
}
