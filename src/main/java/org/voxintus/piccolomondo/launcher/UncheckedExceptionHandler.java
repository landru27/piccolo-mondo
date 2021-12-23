package org.voxintus.piccolomondo.launcher;

import org.apache.logging.log4j.Logger;

public class UncheckedExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final Logger logger;

    public UncheckedExceptionHandler(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error("runtime exception", e);
    }
}
