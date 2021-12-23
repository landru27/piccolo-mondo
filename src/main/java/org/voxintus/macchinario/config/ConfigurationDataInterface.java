package org.voxintus.macchinario.config;

import java.net.URI;

public interface ConfigurationDataInterface {
    public abstract URI getConfigurationURI();

    public abstract EngineSettingsInterface retrieveConfigurationData(URI configURI);
    public abstract void storeConfigurationData(URI configURI);
}
