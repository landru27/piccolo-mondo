/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ */

package org.voxintus.macchinario.config;

public class VideoSettings {
    private static final Boolean defaultVsyncEnabled = true;
    private static final Integer defaultMSAASamples = 4;

    private Boolean currentVsyncEnabled = defaultVsyncEnabled;
    private Integer currentMSAASamples = defaultMSAASamples;

    // VideoSettings uses the singleton pattern
    private static final VideoSettings instance = new VideoSettings();
    private VideoSettings() { }
    public static VideoSettings getInstance() { return instance; }

    public void resetSettings() {
        this.currentVsyncEnabled = defaultVsyncEnabled;
        this.currentMSAASamples = defaultMSAASamples;
    }

    public void setVsyncEnabled(Boolean currentVsyncEnabled) {
        this.currentVsyncEnabled = currentVsyncEnabled;
    }

    public void setMSAASamples(Integer currentMSAASamples) {
        this.currentMSAASamples = currentMSAASamples;
    }
    public Boolean getVsyncEnabled() {
        return currentVsyncEnabled;
    }

    public Integer getMSAASamples() {
        return currentMSAASamples;
    }
}
