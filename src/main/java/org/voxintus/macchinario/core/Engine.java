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

    public void primeEngine() {
        EngineSettingsInterface engineSettings;

        DisplaySettings displaySettings;
        VideoSettings videoSettings;
        AudioSettings audioSettings;
        KeyboardSettings keyboardSettings;
        PointerSettings pointerSettings;
        SaveFileSettings saveFileSettings;

        logger.info("loading engine settings ...");
        engineSettings = this.configurationDataInterface.retrieveConfigurationData(
            this.configurationDataInterface.getConfigurationURI()
        );
        displaySettings = engineSettings.getDisplaySettings();
        videoSettings = engineSettings.getVideoSettings();
        audioSettings = engineSettings.getAudioSettings();
        keyboardSettings = engineSettings.getKeyboardSettings();
        pointerSettings = engineSettings.getPointerSettings();
        saveFileSettings = engineSettings.getSaveFileSettings();
        logger.info(".. engine settings loaded");
    }
}
