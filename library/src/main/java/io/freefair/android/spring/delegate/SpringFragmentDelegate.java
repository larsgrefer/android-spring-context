package io.freefair.android.spring.delegate;

import android.app.Application;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import io.freefair.android.spring.context.ContextHolder;
import lombok.RequiredArgsConstructor;

/**
 * @author Lars Grefer
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
@RequiredArgsConstructor
public class SpringFragmentDelegate {

    private final Fragment fragment;

    public void onCreate(Bundle savedInstanceState) {
        Application application = fragment.getActivity().getApplication();

        ContextHolder.getContext(application).getAutowireCapableBeanFactory().autowireBean(fragment);
    }
}
