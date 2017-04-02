package io.freefair.android.spring.integration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import io.freefair.android.spring.delegate.SpringAppCompatFragmentDelegate;
import lombok.RequiredArgsConstructor;

/**
 * Created by larsgrefer on 02.04.17.
 */
public class SpringAppCompatFragment extends Fragment {

    private final SpringAppCompatFragmentDelegate delegate = new SpringAppCompatFragmentDelegate(this);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
    }
}
