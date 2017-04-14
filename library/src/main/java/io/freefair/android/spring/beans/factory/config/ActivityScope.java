package io.freefair.android.spring.beans.factory.config;

import android.app.Activity;
import android.os.Handler;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lars Grefer
 */
@Slf4j
public class ActivityScope implements Scope {

    private Map<Activity, Map<String, Object>> beans = new HashMap<>();
    private Map<Activity, Map<String, List<Runnable>>> destructionCallbacks = new HashMap<>();

    private ThreadLocal<Activity> currentActivity = new ThreadLocal<>();

    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        if (!beans.containsKey(currentActivity.get())) {
            beans.put(currentActivity.get(), new HashMap<String, Object>());
        }
        Map<String, Object> beanMap = beans.get(currentActivity.get());

        Object bean;

        if (beanMap.containsKey(s)) {
            bean = beanMap.get(s);
        } else {
            bean = objectFactory.getObject();
            beanMap.put(s, bean);
        }

        return bean;
    }

    @Override
    public Object remove(String name) {
        Map<String, Object> beanMap = beans.get(currentActivity.get());
        Object bean = null;
        if (beanMap != null) {
            bean = beanMap.remove(name);
        }

        Map<String, List<Runnable>> destructionCallbackMap = destructionCallbacks.get(currentActivity.get());

        if (destructionCallbackMap != null) {
            destructionCallbackMap.remove(name);
        }

        return bean;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable runnable) {
        if (!destructionCallbacks.containsKey(currentActivity.get())) {
            destructionCallbacks.put(currentActivity.get(), new HashMap<String, List<Runnable>>());
        }
        Map<String, List<Runnable>> destructionCallbackMap = destructionCallbacks.get(currentActivity.get());

        if (!destructionCallbackMap.containsKey(name)) {
            destructionCallbackMap.put(name, new LinkedList<Runnable>());
        }

        destructionCallbackMap.get(name).add(runnable);
    }

    @Override
    public Object resolveContextualObject(String s) {
        switch (s) {
            case "activity":
                return currentActivity.get();
            case "context":
            case "applicationContext":
                return currentActivity.get().getApplicationContext();
            default:
                return null;
        }
    }

    @Override
    public String getConversationId() {
        Activity activity = currentActivity.get();
        return activity == null ? null : activity.toString();
    }

    public void setCurrentActivity(Activity activity) {
        currentActivity.set(activity);
    }

    public void onDestroyActivity(final Activity activity) {
        new Handler(activity.getMainLooper()).post(new DestructionCallbackExecutor(activity));
    }

    @RequiredArgsConstructor
    private class DestructionCallbackExecutor implements Runnable {
        private final Activity activity;

        @Override
        public void run() {
            Map<String, List<Runnable>> destructionCallbackMap = destructionCallbacks.get(activity);
            if (destructionCallbackMap != null && !destructionCallbackMap.isEmpty()) {
                Activity oldActivity = currentActivity.get();
                currentActivity.set(activity);
                for (Map.Entry<String, List<Runnable>> destructionCallbackEntry : destructionCallbackMap.entrySet()) {
                    List<Runnable> runnables = destructionCallbackEntry.getValue();
                    if (runnables != null) {
                        log.info("Calling {} destructionCallback(s) for {}", runnables.size(), destructionCallbackEntry.getKey());
                        for (Runnable runnable : runnables) {
                            runnable.run();
                        }
                    }
                }

                currentActivity.set(oldActivity);
            }
        }
    }
}
