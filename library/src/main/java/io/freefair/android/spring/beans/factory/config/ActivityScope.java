package io.freefair.android.spring.beans.factory.config;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Lars Grefer
 */
public class ActivityScope implements Scope {

    private Map<Activity, Map<String, Object>> beans = new HashMap<>();
    private Map<Activity, List<Runnable>> destructionCallbacks = new HashMap<>();

    private Activity currentActivity = null;

    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        Map<String, Object> beanMap = beans.get(currentActivity);

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
    public Object remove(String s) {
        Map<String, Object> beanMap = beans.get(currentActivity);
        return beanMap.remove(s);
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {
        destructionCallbacks.get(currentActivity).add(runnable);
    }

    @Override
    public Object resolveContextualObject(String s) {
        switch (s) {
            case "activity":
                return currentActivity;
            case "context":
            case "applicationContext":
                return currentActivity.getApplicationContext();
            default:
                return null;
        }
    }

    @Override
    public String getConversationId() {
        return null;
    }

    public void onCreateActivity(Activity activity) {
        currentActivity = activity;
        beans.put(activity, new HashMap<String, Object>());
        destructionCallbacks.put(activity, new LinkedList<Runnable>());
    }

    public void onDestroyActivity(final Activity activity) {
        new Handler(Looper.myLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        for (Runnable runnable : destructionCallbacks.get(activity)) {
                            runnable.run();
                        }
                    }
                });
    }
}
