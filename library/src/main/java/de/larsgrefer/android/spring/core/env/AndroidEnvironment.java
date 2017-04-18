package de.larsgrefer.android.spring.core.env;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.springframework.core.env.StandardEnvironment;
import org.springframework.util.Assert;

public class AndroidEnvironment extends StandardEnvironment {

    public AndroidEnvironment(@NonNull Application application, @Nullable Class<?> buildConfigClass) {
        Assert.notNull(application, "application must not be null");

        try {
            Bundle metaData = application.getPackageManager().getApplicationInfo(application.getPackageName(), PackageManager.GET_META_DATA).metaData;
            getPropertySources().addLast(new BundlePropertySource("applicationInfoMetaData", metaData));
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (buildConfigClass != null) {
            getPropertySources().addLast(new BuildConfigPropertySource("buildConfig", buildConfigClass));
        }

    }
}
