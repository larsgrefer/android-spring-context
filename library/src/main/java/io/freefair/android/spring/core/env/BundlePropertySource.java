package io.freefair.android.spring.core.env;

import android.os.Bundle;

import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Set;


/**
 * An {@link EnumerablePropertySource PropertySource} backend by an Android {@link Bundle}
 *
 * @author Lars Grefer
 */
public class BundlePropertySource extends EnumerablePropertySource<Bundle> {

    public BundlePropertySource(String name, Bundle source) {
        super(name, source);
    }

    @Override
    public String[] getPropertyNames() {
        Set<String> keySet = source.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }

    @Override
    public boolean containsProperty(String name) {
        return source.containsKey(name);
    }

    @Override
    public Object getProperty(String name) {
        return source.get(name);
    }


}
