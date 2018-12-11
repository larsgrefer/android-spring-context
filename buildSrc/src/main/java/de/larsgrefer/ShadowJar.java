package de.larsgrefer;

import com.github.jengelman.gradle.plugins.shadow.internal.GradleVersionUtil;
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowCopyAction;
import org.gradle.api.internal.DocumentationRegistry;
import org.gradle.api.internal.file.copy.CopyAction;

public class ShadowJar extends com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar {

    @Override
    protected CopyAction createCopyAction() {
        DocumentationRegistry documentationRegistry = getServices().get(DocumentationRegistry.class);
        return new ShadowCopyAction(getArchivePath(), getInternalCompressor(), documentationRegistry,
                this.getMetadataCharset(), getTransformers(), getRelocators(), getRootPatternSet(), getStats(),
                new GradleVersionUtil(getProject().getGradle().getGradleVersion()), isPreserveFileTimestamps(), false, null);
    }
}
