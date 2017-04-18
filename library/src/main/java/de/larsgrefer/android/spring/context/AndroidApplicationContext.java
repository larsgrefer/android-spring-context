package de.larsgrefer.android.spring.context;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.googlecode.openbeans.Introspector;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

import de.larsgrefer.android.spring.core.env.AndroidEnvironment;
import de.larsgrefer.android.spring.core.io.AndroidResourceLoader;
import de.larsgrefer.android.spring.core.io.support.AndroidPathMatchingResourcePatternResolver;

/**
 * @author Lars Grefer
 */
public class AndroidApplicationContext extends GenericApplicationContext {

    @NonNull
    private final Application application;

    @Nullable
    private final Class<?> buildConfigClass;

    @NonNull
    private final AnnotatedBeanDefinitionReader reader;

    public AndroidApplicationContext(@NonNull Application application, @Nullable Class<?> buildConfigClass) {
        Assert.notNull(application, "application must not be null");
        Assert.isTrue(buildConfigClass == null || buildConfigClass.getSimpleName().equals("BuildConfig"), "Illegal buildConfigClass");

        this.application = application;
        this.buildConfigClass = buildConfigClass;

        this.reader = new AnnotatedBeanDefinitionReader(this);

        ContextHolder.registerContext(application, this);

        setResourceLoader(new AndroidResourceLoader());

        this.getDefaultListableBeanFactory().registerSingleton("application", application);
        this.registerAlias("application", Introspector.decapitalize(application.getClass().getSimpleName()));

        register(application.getClass());
    }

    @Override
    public String getId() {
        return application.getPackageName();
    }

    @Override
    public String getApplicationName() {
        return application.getApplicationInfo().name;
    }

    @Override
    public String getDisplayName() {
        return application.getApplicationInfo().loadLabel(application.getPackageManager()).toString();
    }

    @Override
    public ApplicationContext getParent() {
        return null;
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return null;
    }

    @Override
    protected ConfigurableEnvironment createEnvironment() {
        return new AndroidEnvironment(application, buildConfigClass);
    }

    @Override
    protected ResourcePatternResolver getResourcePatternResolver() {
        return new AndroidPathMatchingResourcePatternResolver(this);
    }

    public void setEnvironment(ConfigurableEnvironment environment) {
        super.setEnvironment(environment);
        this.reader.setEnvironment(environment);
    }

    public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
        this.reader.setBeanNameGenerator(beanNameGenerator);
        this.getBeanFactory().registerSingleton("org.springframework.context.annotation.internalConfigurationBeanNameGenerator", beanNameGenerator);
    }

    public void setScopeMetadataResolver(ScopeMetadataResolver scopeMetadataResolver) {
        this.reader.setScopeMetadataResolver(scopeMetadataResolver);
    }

    public void register(Class... annotatedClasses) {
        Assert.notEmpty(annotatedClasses, "At least one annotated class must be specified");
        this.reader.register(annotatedClasses);
    }
}
