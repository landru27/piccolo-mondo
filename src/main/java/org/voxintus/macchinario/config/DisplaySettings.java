/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ */

package org.voxintus.macchinario.config;

public class DisplaySettings {
    private static final Integer defaultWindowWidth = 960;
    private static final Integer defaultWindowHeight = 540;
    private static final Boolean defaultIsFullscreen = false;
    private static final String defaultWindowTitle = "title";

    private Integer currentWindowWidth = defaultWindowWidth;
    private Integer currentWindowHeight = defaultWindowHeight;
    private Boolean currentIsFullscreen = defaultIsFullscreen;
    private String currentWindowTitle = defaultWindowTitle;

    private Integer initialWindowPositionX = 0;
    private Integer initialWindowPositionY = 0;

    // DisplaySettings uses the singleton pattern
    private static final DisplaySettings instance = new DisplaySettings();
    private DisplaySettings() { }
    public static DisplaySettings getInstance() { return instance; }

    public void resetSettings() {
        this.currentWindowWidth = defaultWindowWidth;
        this.currentWindowHeight = defaultWindowHeight;
        this.currentIsFullscreen = defaultIsFullscreen;
    }

    public void setWindowWidth(Integer width) {
        this.currentWindowWidth = width;
    }

    public void setWindowHeight(Integer height) {
        this.currentWindowHeight = height;
    }

    public void setIsFullscreen(Boolean fullscreen) {
        this.currentIsFullscreen = fullscreen;
    }

    public void setWindowTitle(String title) {
        this.currentWindowTitle = title;
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

    public String getWindowTitle() {
        return this.currentWindowTitle;
    }

    public void setInitialWindowPositionX(Integer positionX) {
        this.initialWindowPositionX = positionX;
    }

    public void setInitialWindowPositionY(Integer positionY) {
        this.initialWindowPositionY = positionY;
    }

    public Integer getInitialWindowPositionX() {
        return initialWindowPositionX;
    }

    public Integer getInitialWindowPositionY() {
        return initialWindowPositionY;
    }
}
