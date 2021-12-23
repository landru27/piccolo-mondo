/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ */

package org.voxintus.macchinario.config;

import java.net.URI;

public interface ConfigurationDataInterface {
    public abstract URI getConfigurationURI();

    public abstract EngineSettingsInterface retrieveConfigurationData(URI configURI);
    public abstract void storeConfigurationData(URI configURI);
}
