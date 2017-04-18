package de.larsgrefer.android.spring.delegate;

import android.app.Application;
import android.app.Service;
import android.support.annotation.NonNull;

/**
 * @author Lars Grefer
 */
public class SpringServiceDelegate extends AbstractSpringDelegate<Service> {

    public SpringServiceDelegate(@NonNull Service service) {
        super(service);
    }

    public void onCreate() {
        autowireElement();
    }

    public void onDestroy() {

    }

    @Override
    protected Application getApplication() {
        return element.getApplication();
    }
}
