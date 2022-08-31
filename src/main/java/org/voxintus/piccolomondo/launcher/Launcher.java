/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ */

package org.voxintus.piccolomondo.launcher;

import java.io.File;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.voxintus.macchinario.core.*;

public class Launcher {
    private static final String applicationName = ApplicationIdentity.applicationName;
    private static final String applicationAbbr = ApplicationIdentity.applicationAbbr;
    private static final String applicationVersion = ApplicationIdentity.applicationVersion;
    private static final String applicationNameAndVersion = ApplicationIdentity.applicationNameAndVersion;
    private static final String applicationDirectory = ApplicationIdentity.applicationDirectory;

    private static final String defaultApplicationPath = SystemUtils.USER_HOME + File.separator + applicationDirectory;
    private static final String defaultLoggerConfigurationFilename = "log4j2.json";
    private static final String defaultConfigurationFilename = "configuration.json";

    private static final String PROPERTY_ALTERNATIVE_APPLICATION_PATH = "alternativeApplicationPath";
    private static final String PROPERTY_ALTERNATIVE_LOGGER_CONFIGURATION_FILENAME = "alternativeLoggerConfigurationFilename";
    private static final String PROPERTY_ALTERNATIVE_CONFIGURATION_FILENAME = "alternativeConfigurationFilename";

    private static final String PROPERTY_LOG4J_CONFIGURATION_FILE = "log4j.configurationFile";
    private static final String PROPERTY_LOG4J_NAME = "loggerConfigurationName";
    private static final String PROPERTY_LOG_FQFN = "logFullyQualifiedFilename";

    private static final int EXIT_CODE_UNSUPPORTED_OS = 1;

    public static void main(String[] args) {
        String applicationPath;

        String loggerConfigurationFullyQualifiedFilename;
        String loggerConfigurationName;
        String logFullyQualifiedFilename;
        Logger logger;

        String configurationFullyQualifiedFilename;
        ConfigurationDataJSONFile configurationDataJSONFile;

        System.out.println(applicationName + " " + applicationVersion + " launching ...");

        RuntimeOS runningOS = RuntimeEnvironment.detectRunningOS();
        System.out.println("... running on : " + RuntimeEnvironment.reportRunningOS(runningOS));
        exitIfUnsupportedOS(runningOS);

        applicationPath = getApplicationPath();
        System.out.println("... application directory : " + applicationPath);

        loggerConfigurationFullyQualifiedFilename = applicationPath + File.separator + getLoggerConfigurationFilename();
        System.out.println("... logger configuration file : " + loggerConfigurationFullyQualifiedFilename);

        loggerConfigurationName = applicationName;

        logFullyQualifiedFilename = applicationPath +
                File.separator + "logs" +
                File.separator + applicationAbbr + "_" + RuntimeEnvironment.getCurrentFullDate() + ".log";
        System.out.println("... log file : " + logFullyQualifiedFilename);

        logger = initializeLogger(loggerConfigurationFullyQualifiedFilename, applicationName, logFullyQualifiedFilename);

        // now that we have a logger, log the above info that went to System.out so that we capture it
        logBootstrapInfo(
               logger,
               runningOS,
               applicationPath,
               loggerConfigurationFullyQualifiedFilename,
               logFullyQualifiedFilename
        );

        Thread.setDefaultUncaughtExceptionHandler(new UncheckedExceptionHandler(logger));

        configurationFullyQualifiedFilename = applicationPath + File.separator + getConfigurationFilename();
        logger.info("... configuration file : " + configurationFullyQualifiedFilename);

        configurationDataJSONFile = new ConfigurationDataJSONFile(logger, configurationFullyQualifiedFilename);
        new Engine(logger, configurationDataJSONFile).startEngine(applicationNameAndVersion);

        logger.info(applicationName + " exiting");
    }

    private static String getApplicationPath() {
        return getDefaultOrAlternative(defaultApplicationPath, PROPERTY_ALTERNATIVE_APPLICATION_PATH);
    }

    private static String getLoggerConfigurationFilename() {
        return getDefaultOrAlternative(defaultLoggerConfigurationFilename, PROPERTY_ALTERNATIVE_LOGGER_CONFIGURATION_FILENAME);
    }

    private static String getConfigurationFilename() {
        return getDefaultOrAlternative(defaultConfigurationFilename, PROPERTY_ALTERNATIVE_CONFIGURATION_FILENAME);
    }

    private static String getDefaultOrAlternative(String defaultValue, String alternativeProperty) {
        String returnValue = defaultValue;

        String alternativeValue = System.getProperty(alternativeProperty);
        if (alternativeValue != null) {
            returnValue = alternativeValue;
        }

        return returnValue;
    }

    private static Logger initializeLogger(
            String fqfnLoggerConfigurationFilename,
            String loggerConfigurationName,
            String logFullyQualifiedFilename
    ) {
        System.setProperty(PROPERTY_LOG4J_CONFIGURATION_FILE, fqfnLoggerConfigurationFilename);
        System.setProperty(PROPERTY_LOG4J_NAME, loggerConfigurationName);
        System.setProperty(PROPERTY_LOG_FQFN, logFullyQualifiedFilename);

        return LogManager.getLogger(Launcher.class);
    }

    private static void logBootstrapInfo(
            Logger logger,
            RuntimeOS runningOS,
            String applicationPath,
            String loggerConfigurationFullyQualifiedFilename,
            String logFullyQualifiedFilename
    ) {
        logger.info(applicationName + " " + applicationVersion + " launched ...");
        logger.info("... running on : " + RuntimeEnvironment.reportRunningOS(runningOS));
        logger.info("... application directory : " + applicationPath);
        logger.info("... logger configuration file : " + loggerConfigurationFullyQualifiedFilename);
        logger.info("... log file : " + logFullyQualifiedFilename);
    }

    private static void exitIfUnsupportedOS(RuntimeOS os) {
        if (! RuntimeEnvironment.isSupportedOS(os)) {
            System.out.println(applicationName + " exiting; unsupported o/s");
            System.exit(EXIT_CODE_UNSUPPORTED_OS);
        }
    }
}
