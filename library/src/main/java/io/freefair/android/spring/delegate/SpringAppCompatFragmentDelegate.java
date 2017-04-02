package io.freefair.android.spring.delegate;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import io.freefair.android.spring.context.ContextHolder;
import lombok.RequiredArgsConstructor;

/**
 * @author Lars Grefer
 */
@RequiredArgsConstructor
public class SpringAppCompatFragmentDelegate {

    private final Fragment fragment;

    public void onCreate(Bundle savedInstanceState) {
        Application application = fragment.getActivity().getApplication();

        ContextHolder.getContext(application).getAutowireCapableBeanFactory().autowireBean(this);
    }
}
