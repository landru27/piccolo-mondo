/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ */

package org.voxintus.piccolomondo.launcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;

import org.voxintus.macchinario.config.*;

import java.io.File;
import java.net.URI;

public class ConfigurationDataJSONFile implements ConfigurationDataInterface {
    private final Logger logger;

    private final String configurationFQFN;
    private final URI configurationFileURI;

    public ConfigurationDataJSONFile(Logger logger, String fqfn) {
        this.logger = logger;

        this.configurationFQFN = fqfn;
        this.configurationFileURI = new File(this.configurationFQFN).toURI();
    }

    public String getConfigurationFQFN() {
        return this.configurationFQFN;
    }

    @Override
    public URI getConfigurationURI() {
        return this.configurationFileURI;
    }

    @Override
    public EngineSettingsFromJSON retrieveConfigurationData(URI configURI) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json;

        try {
            json = objectMapper.readTree(configURI.toURL());
        } catch (Exception e) {
            logger.error("exception while attempting to read JSON configuration file", e);
            throw new RuntimeException("could not read JSON configuration file");
        }

        return new EngineSettingsFromJSON(logger, json);
    }

    @Override
    public Boolean storeConfigurationData(URI configURI) {
        return false;
    }
}
