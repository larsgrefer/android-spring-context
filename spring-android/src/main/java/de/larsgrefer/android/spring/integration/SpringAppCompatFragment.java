package de.larsgrefer.android.spring.integration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import de.larsgrefer.android.spring.context.ApplicationContextProvider;
import de.larsgrefer.android.spring.delegate.SpringAppCompatFragmentDelegate;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author Lars Grefer
 */
public abstract class SpringAppCompatFragment extends Fragment implements ApplicationContextProvider {

    private final SpringAppCompatFragmentDelegate delegate = new SpringAppCompatFragmentDelegate(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
    }

    @Override
    public GenericApplicationContext getSpringApplicationContext() {
        return delegate.getSpringApplicationContext();
    }
}
