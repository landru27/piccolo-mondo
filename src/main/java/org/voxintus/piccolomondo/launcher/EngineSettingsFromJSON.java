/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ */

package org.voxintus.piccolomondo.launcher;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.Logger;

import org.voxintus.macchinario.config.*;

public class EngineSettingsFromJSON implements EngineSettingsInterface {
    private final Logger logger;

    private final DisplaySettings displaySettings;
    private final VideoSettings videoSettings;

    public EngineSettingsFromJSON(Logger logger, JsonNode json) {
        this.logger = logger;

        this.displaySettings = DisplaySettings.getInstance();
        this.videoSettings = VideoSettings.getInstance();

        this.setDisplaySettingsFromJSON(json);
    }

    @Override
    public DisplaySettings getDisplaySettings(){
        return this.displaySettings;
    }

    @Override
    public VideoSettings getVideoSettings(){
        return this.videoSettings;
    }

    @Override
    public AudioSettings getAudioSettings(){
        return null;
    }

    @Override
    public KeyboardSettings getKeyboardSettings(){
        return null;
    }

    @Override
    public PointerSettings getPointerSettings(){
        return null;
    }

    @Override
    public SaveFileSettings getSaveFileSettings(){
        return null;
    }

    private void setDisplaySettingsFromJSON(JsonNode json) {
        if (this.displaySettings == null) {
            return;
        }

        this.displaySettings.resetSettings();

        JsonNode widthNode = json.at("/engine/display/width");
        if (! widthNode.isMissingNode() && ! widthNode.isNull())  {
            int width = widthNode.asInt();
            this.displaySettings.setWindowWidth(width);
        } else {
            this.logger.warn("empty display width; will use default value");
        }

        JsonNode heightNode = json.at("/engine/display/height");
        if (! heightNode.isMissingNode() && ! heightNode.isNull())  {
            int height = heightNode.asInt();
            this.displaySettings.setWindowHeight(height);
        } else {
            this.logger.warn("empty display height; will use default value");
        }

        JsonNode fullscreenNode = json.at("/engine/display/fullscreen");
        if (! fullscreenNode.isMissingNode() && ! fullscreenNode.isNull())  {
            boolean fullscreen = fullscreenNode.asBoolean();
            this.displaySettings.setIsFullscreen(fullscreen);
        } else {
            this.logger.warn("empty display fullscreen; will use default value");
        }
    }

    private void setVideoSettingsFromJSON(JsonNode json) {
        if (this.videoSettings == null) {
            return;
        }

        this.videoSettings.resetSettings();
        JsonNode videoNode;

        videoNode = json.at("/engine/video/vsync");
        if (! videoNode.isMissingNode() && ! videoNode.isNull())  {
            boolean vsync = videoNode.asBoolean();
            this.videoSettings.setVsyncEnabled(vsync);
        } else {
            this.logger.warn("empty VSync enabled; will use default value");
        }

        videoNode = json.at("/engine/video/msaa-samples");
        if (! videoNode.isMissingNode() && ! videoNode.isNull())  {
            int samples = videoNode.asInt();
            this.videoSettings.setMSAASamples(samples);
        } else {
            this.logger.warn("empty MSAA samples; will use default value");
        }
    }
}
