/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ */

package org.voxintus.piccolomondo.launcher;

import org.voxintus.macchinario.config.*;

import java.io.File;
import java.net.URI;

public class ConfigurationDataJSONFile implements ConfigurationDataInterface {
    private final String configurationFQFN;
    private final URI configurationFileURI;

    public ConfigurationDataJSONFile(String fqfn) {
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
        return new EngineSettingsFromJSON();
    }

    @Override
    public Boolean storeConfigurationData(URI configURI) {
        return false;
    }
}
