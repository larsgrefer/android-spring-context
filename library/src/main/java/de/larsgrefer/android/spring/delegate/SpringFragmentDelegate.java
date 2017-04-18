package de.larsgrefer.android.spring.delegate;

import android.app.Application;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

/**
 * @author Lars Grefer
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class SpringFragmentDelegate extends AbstractSpringDelegate<Fragment> {

    public SpringFragmentDelegate(@NonNull Fragment fragment) {
        super(fragment);
    }

    public void onCreate(Bundle savedInstanceState) {
        autowireElement();
    }

    @Override
    protected Application getApplication() {
        return element.getActivity().getApplication();
    }
}
