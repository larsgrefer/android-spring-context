package io.freefair.android.spring.demo;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by larsgrefer on 02.04.17.
 */
@Component
@Slf4j
@Scope("activity")
public class Dummy2 implements DisposableBean {

    public Dummy2() {
        log.warn("Hallo von d2");
    }

    @Override
    public void destroy() throws Exception {
        log.info("Destroy d2");
    }
}
