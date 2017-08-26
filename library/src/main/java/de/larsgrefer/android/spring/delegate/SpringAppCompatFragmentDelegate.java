package de.larsgrefer.android.spring.delegate;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * @author Lars Grefer
 */
public class SpringAppCompatFragmentDelegate extends AbstractSpringDelegate<Fragment> {

    public SpringAppCompatFragmentDelegate(@NonNull Fragment fragment) {
        super(fragment);
    }

    public void onCreate(Bundle savedInstanceState) {
        autowireElement();
    }

    @Override
    protected Application getApplication() {
        return getElement().getActivity().getApplication();
    }
}
