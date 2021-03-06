package de.larsgrefer.android.spring.core.io.support;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;
import de.larsgrefer.android.spring.core.io.AndroidResourceLoader;
import de.larsgrefer.android.spring.core.io.DexClassResource;
import de.larsgrefer.android.spring.core.io.DexPathResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Lars Grefer
 */
@Slf4j
public class AndroidPathMatchingResourcePatternResolver extends PathMatchingResourcePatternResolver {

    private final WeakHashMap<PathClassLoader, List<DexFile>> dexFilesCache = new WeakHashMap<>();

    public AndroidPathMatchingResourcePatternResolver() {
        this(new AndroidResourceLoader());
    }

    public AndroidPathMatchingResourcePatternResolver(AndroidResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    public AndroidPathMatchingResourcePatternResolver(ClassLoader classLoader) {
        this(new AndroidResourceLoader(classLoader));
    }

    @Override
    protected Set<Resource> doFindAllClassPathResources(String path) throws IOException {
        Set<Resource> resources = super.doFindAllClassPathResources(path);

        PathClassLoader cl = (PathClassLoader) getClassLoader();

        List<DexFile> dexFiles = getDexFiles(cl);

        String preparedPath = path.replace("/", ".");
        for (DexFile dexFile : dexFiles) {

            Enumeration<String> entries = dexFile.entries();

            while (entries.hasMoreElements()) {
                String entry = entries.nextElement();

                if (entry.startsWith(preparedPath)) {
                    log.info("Found {} for path {}", entry, path);
                    resources.add(new ExistingDexPathResource(cl, dexFile, path));
                    break;
                }
            }
        }

        return resources;
    }

    private List<DexFile> getDexFiles(PathClassLoader cl) {

        List<DexFile> result = dexFilesCache.get(cl);
        if(result != null) {
            return result;
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

        result = Collections.unmodifiableList(dexFiles);
        dexFilesCache.put(cl, result);
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

        if(rootDirResource instanceof DexPathResource){
            return doFindPathMatchingDexClassResources((DexPathResource) rootDirResource, subPattern);
        } else {
            return super.doFindPathMatchingFileResources(rootDirResource, subPattern);
        }

    }

    private Set<Resource> doFindPathMatchingDexClassResources(DexPathResource rootDirResource, String subPattern) {
        String pattern = rootDirResource.getPath() + subPattern;

        Enumeration<String> entries = rootDirResource.getDexFile().entries();

        Set<Resource> resources = new HashSet<>();

        while (entries.hasMoreElements()) {
            String entry = entries.nextElement();
            String fakePath = entry.replace('.', '/') + ".class";
            if(getPathMatcher().match(pattern, fakePath)) {
                resources.add(new ExistingDexClassResource(entry, rootDirResource.getClassLoader()));
            }
        }

        return resources;
    }

    /**
     * Subclass of {@link DexPathResource} which shortcuts the expensive existence check.
     */
    private static class ExistingDexPathResource extends DexPathResource {

        ExistingDexPathResource(PathClassLoader cl, DexFile dexFile, String path) {
            super(cl, dexFile, path);
        }

        /**
         * @return true
         */
        @Override
        public boolean exists() {
            return true;
        }

        /**
         * @return true
         */
        @Override
        public Boolean getExists() {
            return true;
        }
    }

    /**
     * Subclass of {@link DexClassResource} which shortcuts the expensive existence check.
     */
    private static class ExistingDexClassResource extends DexClassResource {

        ExistingDexClassResource(String className, ClassLoader classLoader) {
            super(className, classLoader);
        }

        /**
         * @return true
         */
        @Override
        public boolean exists() {
            return true;
        }

        /**
         * @return true
         */
        @Override
        public Boolean getExists() {
            return exists();
        }
    }
}
