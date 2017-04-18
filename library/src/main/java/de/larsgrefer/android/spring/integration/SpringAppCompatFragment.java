package de.larsgrefer.android.spring.integration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import de.larsgrefer.android.spring.delegate.SpringAppCompatFragmentDelegate;

/**
 * @author Lars Grefer
 */
public class SpringAppCompatFragment extends Fragment {

    private final SpringAppCompatFragmentDelegate delegate = new SpringAppCompatFragmentDelegate(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
    }
}
