package org.voxintus.macchinario.core;

import org.apache.logging.log4j.Logger;

public interface Chassis {
    public static Engine createEngine(Logger logger) {
        Engine engine = new Engine(logger);

        return engine;
    }
}
