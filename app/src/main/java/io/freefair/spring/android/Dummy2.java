package io.freefair.spring.android;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by larsgrefer on 02.04.17.
 */
@Component
@Slf4j
public class Dummy2 {

    public Dummy2() {
        log.warn("Hallo von d2");
    }
}
