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

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, engineSettings.getVideoSettings().getMSAASamples());
        glfwWindowHint(GLFW_REFRESH_RATE, glfwVideoMode.refreshRate());

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, this.USE_GLFW_CONTEXT_VERSION_MAJOR);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, this.USE_GLFW_CONTEXT_VERSION_MINOR);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);

        this.grokDisplaySettingsForVideoMode(engineSettings, glfwVideoMode);
        long windowID = glfwCreateWindow(
            engineSettings.getDisplaySettings().getWindowWidth(),
            engineSettings.getDisplaySettings().getWindowHeight(),
            engineSettings.getDisplaySettings().getWindowTitle(),
            engineSettings.getDisplaySettings().getIsFullscreen()
                ? glfwGetPrimaryMonitor()
                : NULL,
            NULL
        );
        glfwSetWindowPos(windowID,
            engineSettings.getDisplaySettings().getInitialWindowPositionX(),
            engineSettings.getDisplaySettings().getInitialWindowPositionY()
        );

        // TODO : move this to a proper keyboard handling module
        glfwSetKeyCallback(windowID, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true);
        });

        glfwMakeContextCurrent(windowID);
        glfwSwapInterval(engineSettings.getVideoSettings().getVsyncEnabled() ? 1 : 0);

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

    private void grokDisplaySettingsForVideoMode(EngineSettingsInterface engineSettings, GLFWVidMode glfwVideoMode) {
        if (engineSettings.getDisplaySettings().getIsFullscreen()) {
            engineSettings.getDisplaySettings().setWindowWidth(glfwVideoMode.width());
            engineSettings.getDisplaySettings().setWindowHeight(glfwVideoMode.height());

            engineSettings.getDisplaySettings().setInitialWindowPositionX(0);
            engineSettings.getDisplaySettings().setInitialWindowPositionY(0);
        } else {
            engineSettings.getDisplaySettings().setInitialWindowPositionX(
                (glfwVideoMode.width() - engineSettings.getDisplaySettings().getWindowWidth()) / 2
            );
            engineSettings.getDisplaySettings().setInitialWindowPositionY(
                (glfwVideoMode.height() - engineSettings.getDisplaySettings().getWindowHeight()) / 2
            );
        }
    }
}
