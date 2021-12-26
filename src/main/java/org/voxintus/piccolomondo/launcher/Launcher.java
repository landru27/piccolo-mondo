/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ */

package org.voxintus.piccolomondo.launcher;

import java.io.File;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.voxintus.macchinario.config.EngineSettingsInterface;
import org.voxintus.macchinario.core.*;

public class Launcher {
    private static final String applicationName = "Piccolo Mondo";
    private static final String applicationAbbr = "piccolomondo";
    private static final String applicationVersion = "v0.1.0";
    private static final String applicationNameAndVersion = applicationName + " - " + applicationVersion;
    private static final String applicationDirectory = "PiccoloMondo";

    private static final String defaultApplicationPath = SystemUtils.USER_HOME + File.separator + applicationDirectory;
    private static final String defaultLoggerConfigurationFilename = "log4j2.json";
    private static final String defaultConfigurationFilename = "configuration.json";

    private static final String PROPERTY_ALTERNATIVE_APPLICATION_PATH = "alternativeApplicationPath";
    private static final String PROPERTY_ALTERNATIVE_LOGGER_CONFIGURATION_FILENAME = "alternativeLoggerConfigurationFilename";
    private static final String PROPERTY_ALTERNATIVE_CONFIGURATION_FILENAME = "alternativeConfigurationFilename";

    private static final String PROPERTY_LOG4J_CONFIGURATION_FILE = "log4j.configurationFile";
    private static final String PROPERTY_LOG_FQFN = "logFullyQualifiedFilename";

    public static void main(String[] args) {
        String applicationPath;

        String loggerConfigurationFilename;
        String loggerConfigurationFullyQualifiedFilename;
        String logFilename;
        String logFullyQualifiedFilename;

        String configurationFilename;
        String configurationFullyQualifiedFilename;

        Logger logger;

        ConfigurationDataJSONFile configurationDataJSONFile;
        Engine engine;
        EngineSettingsInterface engineSettings;
        EngineStatus engineOutcome;

        System.out.println(applicationName + " " + applicationVersion + " launching ...");

        RuntimeOS runningOS = RuntimeEnvironment.detectRunningOS();
        System.out.println("... running on : " + RuntimeEnvironment.reportRunningOS(runningOS));
        if (! RuntimeEnvironment.isSupportedOS(runningOS)) {
            System.out.println(applicationName + " exiting; unsupported o/s");
        }

        applicationPath = getApplicationPath();
        System.out.println("... application directory : " + applicationPath);

        loggerConfigurationFilename = getLoggerConfigurationFilename();
        loggerConfigurationFullyQualifiedFilename = applicationPath + File.separator + loggerConfigurationFilename;
        System.out.println("... logger configuration file : " + loggerConfigurationFullyQualifiedFilename);

        logFilename = applicationAbbr + "_" + RuntimeEnvironment.getCurrentFullDate() + ".log";
        logFullyQualifiedFilename = applicationPath + File.separator + "logs" + File.separator + logFilename;
        System.out.println("... log file : " + logFullyQualifiedFilename);

        logger = initializeLogger(loggerConfigurationFullyQualifiedFilename, logFullyQualifiedFilename);

        // log the above info that went to System.out so that we capture it
        logger.info(applicationName + " " + applicationVersion + " launched ...");
        logger.info("... running on : " + RuntimeEnvironment.reportRunningOS(runningOS));
        logger.info("... application directory : " + applicationPath);
        logger.info("... logger configuration file : " + loggerConfigurationFullyQualifiedFilename);
        logger.info("... log file : " + logFullyQualifiedFilename);

        Thread.setDefaultUncaughtExceptionHandler(new UncheckedExceptionHandler(logger));

        configurationFilename = getConfigurationFilename();
        configurationFullyQualifiedFilename = applicationPath + File.separator + configurationFilename;
        logger.info("... configuration file : " + configurationFullyQualifiedFilename);

        configurationDataJSONFile = new ConfigurationDataJSONFile(logger, configurationFullyQualifiedFilename);
        engine = new Engine(logger, configurationDataJSONFile);
        engineSettings = engine.primeEngine(applicationNameAndVersion);
        engineOutcome = engine.igniteEngine(engineSettings);
        logger.info("engine shutdown : " + engineOutcome);

        logger.info(applicationName + " exiting");
    }

    private static String getApplicationPath() {
        String applicationPath = defaultApplicationPath;

        String alternativeApplicationPath = System.getProperty(PROPERTY_ALTERNATIVE_APPLICATION_PATH);
        if (alternativeApplicationPath != null) {
            applicationPath = alternativeApplicationPath;
        }

        return applicationPath;
    }

    private static String getLoggerConfigurationFilename() {
        String loggerConfigurationFilename = defaultLoggerConfigurationFilename;

        String alternativeLoggerConfigurationFilename = System.getProperty(PROPERTY_ALTERNATIVE_LOGGER_CONFIGURATION_FILENAME);
        if (alternativeLoggerConfigurationFilename != null) {
            loggerConfigurationFilename = alternativeLoggerConfigurationFilename;
        }

        return loggerConfigurationFilename;
    }

    private static String getConfigurationFilename() {
        String configurationFilename = defaultConfigurationFilename;

        String alternativeConfigurationFilename = System.getProperty(PROPERTY_ALTERNATIVE_CONFIGURATION_FILENAME);
        if (alternativeConfigurationFilename != null) {
            configurationFilename = alternativeConfigurationFilename;
        }

        return configurationFilename;
    }

    private static Logger initializeLogger(String fqfnLoggerConfigurationFilename, String logFullyQualifiedFilename) {
        System.setProperty(PROPERTY_LOG4J_CONFIGURATION_FILE, fqfnLoggerConfigurationFilename);
        System.setProperty(PROPERTY_LOG_FQFN, logFullyQualifiedFilename);

        return LogManager.getLogger(Launcher.class);
    }
}
