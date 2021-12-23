/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ */

package org.voxintus.macchinario.config;

public class DisplaySettings {
    private static final Integer defaultWindowWidth = 960;
    private static final Integer defaultWindowHeight = 540;
    private static final Boolean defaultIsFullscreen = false;

    private Integer currentWindowWidth = defaultWindowWidth;
    private Integer currentWindowHeight = defaultWindowHeight;
    private Boolean currentIsFullscreen = defaultIsFullscreen;

    // DisplaySettings uses the singleton pattern
    private static final DisplaySettings instance = new DisplaySettings();
    private DisplaySettings() { }
    public static DisplaySettings getInstance() { return instance; }

    public void setWindowWidth(Integer width) {
        this.currentWindowWidth = width;
    }

    public void setWindowHeight(Integer height) {
        this.currentWindowHeight = height;
    }

    public void setIsFullscreen(Boolean fullscreen) {
        this.currentIsFullscreen = fullscreen;
    }

    public Integer getWindowWidth() {
        return this.currentWindowWidth;
    }

    public Integer getWindowHeight() {
        return this.currentWindowHeight;
    }

    public Boolean getIsFullscreen() {
        return this.currentIsFullscreen;
    }
}
