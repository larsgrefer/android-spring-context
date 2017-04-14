package io.freefair.android.spring.integration;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import io.freefair.android.spring.delegate.SpringFragmentDelegate;

import static android.os.Build.VERSION_CODES.HONEYCOMB;

/**
 * @author Lars Grefer
 */
@RequiresApi(api = HONEYCOMB)
public class SpringFragment extends Fragment {

    private final SpringFragmentDelegate delegate = new SpringFragmentDelegate(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
    }

}
