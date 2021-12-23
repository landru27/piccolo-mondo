package org.voxintus.macchinario.config;

public interface EngineSettingsInterface {
    public abstract DisplaySettings getDisplaySettings();
    public abstract VideoSettings getVideoSettings();
    public abstract AudioSettings getAudioSettings();
    public abstract KeyboardSettings getKeyboardSettings();
    public abstract PointerSettings getPointerSettings();
    public abstract SaveFileSettings getSaveFileSettings();
}
