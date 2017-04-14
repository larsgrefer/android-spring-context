package io.freefair.android.spring.core.io.support;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;
import io.freefair.android.spring.core.io.DexClassResource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lars Grefer
 */
@Slf4j
public class AndroidPathMatchingResourcePatternResolver extends PathMatchingResourcePatternResolver {

    private final WeakHashMap<PathClassLoader, List<String>> dexEntriesCache = new WeakHashMap<>();

    public AndroidPathMatchingResourcePatternResolver(ResourceLoader androidApplicationContext) {
        super(androidApplicationContext);
    }

    @Override
    protected Set<Resource> doFindAllClassPathResources(String path) throws IOException {
        StopWatch stopWatch = new StopWatch("doFindAllClassPathResources");
        boolean debugLog = log.isDebugEnabled();
        if(debugLog) {
            stopWatch.start("spring-scan");
        }
        Set<Resource> resources = super.doFindAllClassPathResources(path);

        if (debugLog) {
            stopWatch.stop();
            stopWatch.start("dex-scan");
        }

        PathClassLoader cl = (PathClassLoader) getClassLoader();

        List<String> dexEntries = getDexEntries(cl);

        for (String entry : dexEntries) {
            if (entry.startsWith(path.replace("/", "."))) {
                log.info("Found {}", entry);
                resources.add(new DexClassResource(entry, cl));
            }
        }

        if (debugLog) {
            stopWatch.stop();
            log.debug(stopWatch.prettyPrint());
        }

        return resources;
    }

    private List<String> getDexEntries(PathClassLoader cl) {

        List<String> result = dexEntriesCache.get(cl);
        if(result != null) {
            return result;
        } else {
            result = new LinkedList<>();
        }


        Field pathListField = ReflectionUtils.findField(PathClassLoader.class, "pathList");
        Field mDexsField = ReflectionUtils.findField(PathClassLoader.class, "mDexs");

        List<DexFile> dexFiles;

        if(pathListField != null) {
            dexFiles = resolveDexFilesFromPathList(cl, pathListField);
        } else if(mDexsField != null) {
            dexFiles = resolveDexFilesFromMDexs(cl, mDexsField);
        } else {
            throw new RuntimeException("Unsupported android version (can't resolve DexFile's from ClassLoader)");
        }

        for (DexFile dexFile : dexFiles) {
            Enumeration<String> entries = dexFile.entries();

            while (entries.hasMoreElements()) {
                String entry = entries.nextElement();
                result.add(entry);
            }
        }

        result = Collections.unmodifiableList(result);
        dexEntriesCache.put(cl, result);
        return result;
    }

    private static List<DexFile> resolveDexFilesFromMDexs(PathClassLoader cl, Field mDexsField) {
        ReflectionUtils.makeAccessible(mDexsField);
        DexFile[] mDexs = (DexFile[]) ReflectionUtils.getField(mDexsField, cl);
        return Arrays.asList(mDexs);
    }

    private static List<DexFile> resolveDexFilesFromPathList(PathClassLoader cl, Field pathListField) {
        List<DexFile> result = new LinkedList<>();

        ReflectionUtils.makeAccessible(pathListField);
        Object pathList = ReflectionUtils.getField(pathListField, cl);

        Field dexElementsField = ReflectionUtils.findField(pathList.getClass(), "dexElements");
        ReflectionUtils.makeAccessible(dexElementsField);
        Object[] dexElements = (Object[]) ReflectionUtils.getField(dexElementsField, pathList);

        if(dexElements.length > 0) {
            Field dexFileField = ReflectionUtils.findField(dexElements[0].getClass(), "dexFile");
            ReflectionUtils.makeAccessible(dexFileField);

            for (Object dexElement : dexElements) {
                DexFile dexFile = (DexFile) ReflectionUtils.getField(dexFileField, dexElement);

                if(dexFile != null) {
                    result.add(dexFile);
                }
            }
        }

        return result;
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
