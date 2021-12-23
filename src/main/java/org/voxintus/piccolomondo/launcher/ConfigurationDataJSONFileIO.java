package org.voxintus.piccolomondo.launcher;

import org.voxintus.macchinario.config.*;

import java.io.File;
import java.net.URI;

public class ConfigurationDataJSONFileIO implements ConfigurationDataInterface {
    private final String configurationFQFN;
    private final URI configurationFileURI;

    public ConfigurationDataJSONFileIO(String fqfn) {
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
        return null;
    }

    @Override
    public void storeConfigurationData(URI configURI) {
    }
}
