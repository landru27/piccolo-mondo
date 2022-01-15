/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ */

package org.voxintus.macchinario.core;

import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.voxintus.macchinario.config.*;

public class Engine {
    private final Logger logger;
    private final ConfigurationDataInterface configurationDataInterface;

    private final int USE_GLFW_CONTEXT_VERSION_MAJOR = 4;
    private final int USE_GLFW_CONTEXT_VERSION_MINOR = 1;

    public Engine(Logger logger, ConfigurationDataInterface config) {
        this.logger = logger;
        this.configurationDataInterface = config;
    }

    public void startEngine(String applicationFullName) {
        EngineSettingsInterface engineSettings = this.primeEngine(applicationFullName);
        EngineStatus engineOutcome = this.igniteEngine(engineSettings);

        this.logger.info("engine shutdown : " + engineOutcome);
    }

    public EngineSettingsInterface primeEngine(String applicationFullName) {
        EngineSettingsInterface engineSettings;

        logger.info("loading engine settings ...");
        engineSettings = this.configurationDataInterface.retrieveConfigurationData(
            this.configurationDataInterface.getConfigurationURI()
        );
        if (engineSettings == null) {
            logger.error("could not retrieve engine settings");
            return null;
        }
        logger.info("... engine settings loaded");

        engineSettings.getDisplaySettings().setWindowTitle(applicationFullName);

        return engineSettings;
    }

    public EngineStatus igniteEngine(EngineSettingsInterface engineSettings) {
        long windowID;

        if (engineSettings == null) {
            logger.error("could not ignite engine with null engine settings");
            return EngineStatus.MISSING_SETTINGS;
        }

        windowID = this.initializeGraphicsLibraryFramework(engineSettings);
        this.operateGraphicsLibraryFramework(windowID);
        this.shutdownGraphicsLibraryFramework(windowID);

        return EngineStatus.MAIN_LOOP_COMPLETE;
    }

    private long initializeGraphicsLibraryFramework(EngineSettingsInterface engineSettings) {
        logger.info("initializing GLFW");

        // TODO : can this be set to use the logger?
        GLFWErrorCallback.createPrint(System.err).set();

        boolean glfwInitialized = glfwInit();
        assert glfwInitialized;

        GLFWVidMode glfwVideoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        assert glfwVideoMode != null;

        long windowID = initializeWindow(
            glfwVideoMode,
            engineSettings.getVideoSettings(),
            engineSettings.getDisplaySettings()
        );
        assert windowID > 0;

        return windowID;
    }

    private long initializeWindow(GLFWVidMode glfwVideoMode, VideoSettings videoSettings, DisplaySettings displaySettings) {
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, videoSettings.getMSAASamples());
        glfwWindowHint(GLFW_REFRESH_RATE, glfwVideoMode.refreshRate());

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, this.USE_GLFW_CONTEXT_VERSION_MAJOR);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, this.USE_GLFW_CONTEXT_VERSION_MINOR);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);

        this.grokDisplaySettingsForVideoMode(glfwVideoMode, displaySettings);
        long windowID = glfwCreateWindow(
            displaySettings.getWindowWidth(),
            displaySettings.getWindowHeight(),
            displaySettings.getWindowTitle(),
            displaySettings.getIsFullscreen()
                ? glfwGetPrimaryMonitor()
                : NULL,
            NULL
        );
        glfwSetWindowPos(windowID,
            displaySettings.getInitialWindowPositionX(),
            displaySettings.getInitialWindowPositionY()
        );

        // TODO : move this to a proper keyboard handling module
        glfwSetKeyCallback(windowID, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true);
        });

        glfwMakeContextCurrent(windowID);
        glfwSwapInterval(videoSettings.getVsyncEnabled() ? 1 : 0);

        glfwShowWindow(windowID);

        return windowID;
    }

    private void operateGraphicsLibraryFramework(long windowID) {
        logger.info("entering GLFW loop");

        GL.createCapabilities();

        // TODO : simple demo display, from lwjgl.org
        glClearColor(0.4f, 0.0f, 0.0f, 0.0f);
        while ( !glfwWindowShouldClose(windowID) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glfwSwapBuffers(windowID);

            glfwPollEvents();
        }
    }

    private void shutdownGraphicsLibraryFramework(long windowID) {
        logger.info("shutting down GLFW");

        glfwFreeCallbacks(windowID);
        glfwDestroyWindow(windowID);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void grokDisplaySettingsForVideoMode(GLFWVidMode glfwVideoMode, DisplaySettings displaySettings) {
        if (displaySettings.getIsFullscreen()) {
            displaySettings.setWindowWidth(glfwVideoMode.width());
            displaySettings.setWindowHeight(glfwVideoMode.height());

            displaySettings.setInitialWindowPositionX(0);
            displaySettings.setInitialWindowPositionY(0);
        } else {
            displaySettings.setInitialWindowPositionX(
                (glfwVideoMode.width() - displaySettings.getWindowWidth()) / 2
            );
            displaySettings.setInitialWindowPositionY(
                (glfwVideoMode.height() - displaySettings.getWindowHeight()) / 2
            );
        }
    }
}
