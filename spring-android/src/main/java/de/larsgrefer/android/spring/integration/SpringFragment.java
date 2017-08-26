package de.larsgrefer.android.spring.integration;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import de.larsgrefer.android.spring.context.ApplicationContextProvider;
import de.larsgrefer.android.spring.delegate.SpringFragmentDelegate;
import org.springframework.context.support.GenericApplicationContext;

import static android.os.Build.VERSION_CODES.HONEYCOMB;

/**
 * @author Lars Grefer
 */
@RequiresApi(api = HONEYCOMB)
public abstract class SpringFragment extends Fragment implements ApplicationContextProvider {

    private final SpringFragmentDelegate delegate = new SpringFragmentDelegate(this);

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
