/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ */

package org.voxintus.piccolomondo.launcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationDataJSONFileTests {
    @Test
    @DisplayName("attempt to read nonexistent file")
    public void attemptToReadNonexistentFile() {
        Logger logger = LogManager.getLogger();
        ConfigurationDataJSONFile configurationDataJSONFile = new ConfigurationDataJSONFile(logger, "/tmp/configuration-data-json-file-tests--non-existent-file");

        EngineSettingsFromJSON actual = configurationDataJSONFile.retrieveConfigurationData(
            configurationDataJSONFile.getConfigurationURI()
        );
        assertNull(actual);
    }

    @Test
    @DisplayName("attempt to read empty file")
    public void attemptToReadEmptyFile() throws IOException {
        Logger logger = LogManager.getLogger();

        byte[] data = "".getBytes();
        Path file = Paths.get("/tmp/configuration-data-json-file-tests--empty-file");
        Files.write(file, data);

        ConfigurationDataJSONFile configurationDataJSONFile = new ConfigurationDataJSONFile(logger, "/tmp/configuration-data-json-file-tests--empty-file");

        EngineSettingsFromJSON actual = configurationDataJSONFile.retrieveConfigurationData(
                configurationDataJSONFile.getConfigurationURI()
        );
        assertNotNull(actual);

        Integer actualDisplayWidth = actual.getDisplaySettings().getWindowWidth();
        assertEquals(960, actualDisplayWidth);
        Integer actualDisplayHeight = actual.getDisplaySettings().getWindowHeight();
        assertEquals(540, actualDisplayHeight);
        Boolean actualDisplayFullscreen = actual.getDisplaySettings().getIsFullscreen();
        assertEquals(false, actualDisplayFullscreen);
    }

    @Test
    @DisplayName("attempt to read basic file")
    public void attemptToReadBasicFile() throws IOException {
        Logger logger = LogManager.getLogger();

        byte[] data = "{\"engine\": {\"display\": {\"width\": 611, \"height\": 344, \"fullscreen\": true}}}".getBytes();
        Path file = Paths.get("/tmp/configuration-data-json-file-tests--basic-file");
        Files.write(file, data);

        ConfigurationDataJSONFile configurationDataJSONFile = new ConfigurationDataJSONFile(logger, "/tmp/configuration-data-json-file-tests--basic-file");

        EngineSettingsFromJSON actual = configurationDataJSONFile.retrieveConfigurationData(
                configurationDataJSONFile.getConfigurationURI()
        );
        assertNotNull(actual);

        Integer actualDisplayWidth = actual.getDisplaySettings().getWindowWidth();
        assertEquals(611, actualDisplayWidth);
        Integer actualDisplayHeight = actual.getDisplaySettings().getWindowHeight();
        assertEquals(344, actualDisplayHeight);
        Boolean actualDisplayFullscreen = actual.getDisplaySettings().getIsFullscreen();
        assertEquals(true, actualDisplayFullscreen);
    }
}
