/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ */

package org.voxintus.macchinario.config;

public interface EngineSettingsInterface {
    public abstract DisplaySettings getDisplaySettings();
    public abstract VideoSettings getVideoSettings();
    public abstract AudioSettings getAudioSettings();
    public abstract KeyboardSettings getKeyboardSettings();
    public abstract PointerSettings getPointerSettings();
    public abstract SaveFileSettings getSaveFileSettings();
}
