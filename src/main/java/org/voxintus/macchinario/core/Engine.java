/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ */

package org.voxintus.macchinario.core;

import org.apache.logging.log4j.Logger;

import org.voxintus.macchinario.config.*;

public class Engine {
    private final Logger logger;
    private final ConfigurationDataInterface configurationDataInterface;

    public Engine(Logger logger, ConfigurationDataInterface config) {
        this.logger = logger;
        this.configurationDataInterface = config;
    }

    public EngineSettingsInterface primeEngine() {
        EngineSettingsInterface engineSettings;

        logger.info("loading engine settings ...");
        engineSettings = this.configurationDataInterface.retrieveConfigurationData(
            this.configurationDataInterface.getConfigurationURI()
        );
        if (engineSettings == null) {
            logger.error("could not retrieve engine settings");
            return null;
        }
        logger.info("... engine settings loaded");

        return engineSettings;
    }

    public EngineStatus igniteEngine(EngineSettingsInterface engineSettings) {
        DisplaySettings displaySettings;
        VideoSettings videoSettings;
        AudioSettings audioSettings;
        KeyboardSettings keyboardSettings;
        PointerSettings pointerSettings;
        SaveFileSettings saveFileSettings;

        if (engineSettings == null) {
            logger.error("could not ignite engine with null engine settings");
            return EngineStatus.MISSING_SETTINGS;
        }

        displaySettings = engineSettings.getDisplaySettings();
        videoSettings = engineSettings.getVideoSettings();
        audioSettings = engineSettings.getAudioSettings();
        keyboardSettings = engineSettings.getKeyboardSettings();
        pointerSettings = engineSettings.getPointerSettings();
        saveFileSettings = engineSettings.getSaveFileSettings();

        logger.debug("display width : " + displaySettings.getWindowWidth());
        logger.debug("display height : " + displaySettings.getWindowHeight());
        logger.debug("display fullscreen : " + displaySettings.getIsFullscreen());

        return EngineStatus.MAIN_LOOP_NOT_STARTED;
    }
}
