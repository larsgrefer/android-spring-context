package de.larsgrefer.android.spring.context;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class LocaleUtil {

    @NonNull
    public static Locale getLocale(@NonNull Context context) {
        return getLocale(context.getResources());
    }

    @NonNull
    public static Locale getLocale(@NonNull Resources resources) {
        return getLocale(resources.getConfiguration());
    }

    @NonNull
    public static Locale getLocale(@NonNull Configuration configuration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return configuration.getLocales().get(0);
        } else {
            return configuration.locale;
        }
    }
}
