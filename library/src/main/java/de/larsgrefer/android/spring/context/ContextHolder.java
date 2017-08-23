package de.larsgrefer.android.spring.context;

import android.app.Application;
import org.springframework.context.support.GenericApplicationContext;

import java.util.WeakHashMap;

public final class ContextHolder {

    private final static WeakHashMap<Application, GenericApplicationContext> contexts = new WeakHashMap<>();

    static void registerContext(Application application, GenericApplicationContext aac){
        contexts.put(application, aac);
    }

    public static GenericApplicationContext getContext(Application application) {
        return contexts.get(application);
    }
}
