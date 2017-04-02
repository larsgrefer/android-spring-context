package io.freefair.android.spring.core.env;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Bundle;

import org.springframework.core.env.StandardEnvironment;

public class AndroidEnvironment extends StandardEnvironment {

    public AndroidEnvironment(Application application) {

        try {
            Bundle metaData = application.getPackageManager().getApplicationInfo(application.getPackageName(), PackageManager.GET_META_DATA).metaData;
            getPropertySources().addLast(new BundlePropertySource("applicationInfoMetaData", metaData));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}
