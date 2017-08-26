package de.larsgrefer.android.spring.context;

import org.springframework.context.support.GenericApplicationContext;

public interface ApplicationContextProvider {

    GenericApplicationContext getSpringApplicationContext();
}
