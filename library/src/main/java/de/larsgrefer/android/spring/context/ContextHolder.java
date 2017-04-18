package de.larsgrefer.android.spring.context;

import android.app.Application;

import java.util.WeakHashMap;

public final class ContextHolder {

    private final static WeakHashMap<Application, AndroidApplicationContext> contexts = new WeakHashMap<>();

    static void registerContext(Application application, AndroidApplicationContext aac){
        contexts.put(application, aac);
    }

    public static AndroidApplicationContext getContext(Application application) {
        return contexts.get(application);
    }
}
