package de.larsgrefer.android.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * Created by larsgrefer on 02.04.17.
 */
@Component
@Slf4j
public class Dummy2 implements DisposableBean {

    public Dummy2() {
        log.warn("Hallo von d2");
    }

    @Override
    public void destroy() throws Exception {
        log.info("Destroy d2");
    }
}
