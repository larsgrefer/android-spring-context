package io.freefair.android.spring.context;

import android.app.Application;

import com.googlecode.openbeans.Introspector;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import io.freefair.android.spring.core.env.AndroidEnvironment;
import io.freefair.android.spring.core.io.AndroidResourceLoader;
import io.freefair.android.spring.core.io.support.AndroidPathMatchingResourcePatternResolver;

/**
 * @author Lars Grefer
 */
public class AndroidApplicationContext extends GenericApplicationContext {

    private Application application;

    AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader;
    XmlBeanDefinitionReader xmlBeanDefinitionReader;

    public AndroidApplicationContext(Application application) {
        if(application == null) {
            throw new NullPointerException("application");
        }
        this.application = application;
        ContextHolder.registerContext(application, this);

        setResourceLoader(new AndroidResourceLoader());

        this.getDefaultListableBeanFactory().registerSingleton("application", application);
        this.registerAlias("application", Introspector.decapitalize(application.getClass().getSimpleName()));

        annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(this);
        xmlBeanDefinitionReader = new XmlBeanDefinitionReader(this);

        annotatedBeanDefinitionReader.register(application.getClass());
    }

    @Override
    public String getId() {
        return application.getApplicationInfo().packageName;
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
        return new AndroidEnvironment(application);
    }

    @Override
    protected ResourcePatternResolver getResourcePatternResolver() {
        return new AndroidPathMatchingResourcePatternResolver(this);
    }
}
