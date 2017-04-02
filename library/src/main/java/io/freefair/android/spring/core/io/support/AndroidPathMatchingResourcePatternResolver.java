package io.freefair.android.spring.core.io.support;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Ref;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Set;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;
import io.freefair.android.spring.core.io.DexClassResource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AndroidPathMatchingResourcePatternResolver extends PathMatchingResourcePatternResolver {

    public AndroidPathMatchingResourcePatternResolver(ResourceLoader androidApplicationContext) {
        super(androidApplicationContext);
    }

    @Override
    protected Set<Resource> doFindAllClassPathResources(String path) throws IOException {
        Set<Resource> resources = super.doFindAllClassPathResources(path);

        PathClassLoader cl = (PathClassLoader) getClassLoader();

        Field pathListField = ReflectionUtils.findField(PathClassLoader.class, "pathList");
        ReflectionUtils.makeAccessible(pathListField);
        Object pathList = ReflectionUtils.getField(pathListField, cl);

        Field dexElementsField = ReflectionUtils.findField(pathList.getClass(), "dexElements");
        ReflectionUtils.makeAccessible(dexElementsField);
        Object[] dexElements = (Object[]) ReflectionUtils.getField(dexElementsField, pathList);

        if(dexElements.length > 0) {
            Field dexFileField = ReflectionUtils.findField(dexElements[0].getClass(), "dexFile");
            Field urlHandlerField = ReflectionUtils.findField(dexElements[0].getClass(), "urlHandler");
            ReflectionUtils.makeAccessible(dexFileField);
            ReflectionUtils.makeAccessible(urlHandlerField);

            for (Object dexElement : dexElements) {
                DexFile dexFile = (DexFile) ReflectionUtils.getField(dexFileField, dexElement);
                Object urlHandler = ReflectionUtils.getField(urlHandlerField, dexElement);

                if(dexFile != null) {
                    Enumeration<String> entries = dexFile.entries();

                    while (entries.hasMoreElements()) {
                        String entry = entries.nextElement();
                        if(entry.startsWith(path.replace("/", "."))) {
                            log.info("Found {}", entry);
                            resources.add(new DexClassResource(entry, cl));
                        }
                    }
                }
            }
        }

        return resources;
    }

    protected Set<Resource> doFindPathMatchingFileResources(Resource rootDirResource, String subPattern)
            throws IOException {

        if(rootDirResource instanceof DexClassResource && subPattern.endsWith("*.class")){
            return Collections.singleton(rootDirResource);
        } else {
            return super.doFindPathMatchingFileResources(rootDirResource, subPattern);
        }

    }
}
