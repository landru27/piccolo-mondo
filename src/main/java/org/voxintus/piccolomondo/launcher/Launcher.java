package org.voxintus.piccolomondo.launcher;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.voxintus.macchinario.core.*;

public class Launcher {
    private static final String applicationName = "Piccolo Mondo";
    private static final String applicationAbbr = "piccolomondo";
    private static final String applicationVersion = "v0.1.0";
    private static final String applicationDirectory = "PiccoloMondo_v0";

    private static final String defaultApplicationPath = SystemUtils.USER_HOME + File.separator + applicationDirectory;
    private static final String defaultLoggerConfigurationFilename = "log4j2.json";
    private static final String defaultConfigurationFilename = "configuration.json";

    private static final String PROPERTY_ALTERNATIVE_APPLICATION_PATH = "alternativeApplicationPath";
    private static final String PROPERTY_ALTERNATIVE_LOGGER_CONFIGURATION_FILENAME = "alternativeLoggerConfigurationFilename";
    private static final String PROPERTY_ALTERNATIVE_CONFIGURATION_FILENAME = "alternativeConfigurationFilename";

    private static final String PROPERTY_LOG4J_CONFIGURATION_FILE = "log4j.configurationFile";
    private static final String PROPERTY_FQFN_FILENAME = "fqfnLogFilename";
    private static final String LOG_FILENAME_DATE_FORMAT = "YYYY-MM-dd";

    public static void main(String[] args) {
        String applicationPath;

        String loggerConfigurationFilename;
        String fqfnLoggerConfigurationFilename;
        String logFilename;
        String fqfnLogFilename;

        String configurationFilename;
        String fqfnConfigurationFilename;

        Logger logger;

        ConfigurationDataJSONFileIO configurationDataJSONFileIO;
        Engine engine;

        System.out.println(applicationName + " " + applicationVersion + " launching ...");

        System.out.println("... running on : " + reportRunningOS());

        applicationPath = getApplicationPath();
        System.out.println("... application directory : " + applicationPath);

        loggerConfigurationFilename = getLoggerConfigurationFilename();
        fqfnLoggerConfigurationFilename = applicationPath + File.separator + loggerConfigurationFilename;
        System.out.println("... logger configuration file : " + fqfnLoggerConfigurationFilename);

        logFilename = applicationAbbr + "_" + getCurrentFullDate() + ".log";
        fqfnLogFilename = applicationPath + File.separator + "logs" + File.separator + logFilename;
        System.out.println("... log file : " + fqfnLogFilename);

        logger = initializeLogger(fqfnLoggerConfigurationFilename, fqfnLogFilename);
        logger.info(applicationName + " " + applicationVersion + " launched ...");
        logger.info("... running on : " + reportRunningOS());
        logger.info("... application directory : " + applicationPath);
        logger.info("... logger configuration file : " + fqfnLoggerConfigurationFilename);
        logger.info("... log file : " + fqfnLogFilename);

        configurationFilename = getConfigurationFilename();
        fqfnConfigurationFilename = applicationPath + File.separator + configurationFilename;
        logger.info("... configuration file : " + fqfnConfigurationFilename);

        configurationDataJSONFileIO = new ConfigurationDataJSONFileIO(fqfnConfigurationFilename);
        engine = new Engine(logger, configurationDataJSONFileIO);
        engine.primeEngine();

        logger.info(applicationName + " exiting");
    }

    private static String reportRunningOS() {
        String runningOS = SystemUtils.OS_NAME + ", " + SystemUtils.OS_VERSION + ", " + SystemUtils.OS_ARCH;

        if (SystemUtils.IS_OS_MAC_OSX) {
            return "MacOS X" + " (" + runningOS + ") [supported]";
        }
        if (SystemUtils.IS_OS_LINUX) {
            return "Linux" + " (" + runningOS + ") [supported]";
        }
        if (SystemUtils.IS_OS_WINDOWS) {
            return "Microsoft Windows" + " (" + runningOS + ") [supported]";
        }
        if (SystemUtils.IS_OS_UNIX) {
            return "UNIX" + " (" + runningOS + ")" + " [best-effort support]";
        }

        return "unrecognized o/s" + " (" + runningOS + ")" + " [unsupported]";
    }

    private static String getCurrentFullDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(LOG_FILENAME_DATE_FORMAT));
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

    private static Logger initializeLogger(String fqfnLoggerConfigurationFilename, String fqfnLogFilename) {
        System.setProperty(PROPERTY_LOG4J_CONFIGURATION_FILE, fqfnLoggerConfigurationFilename);
        System.setProperty(PROPERTY_FQFN_FILENAME, fqfnLogFilename);

        return LogManager.getLogger(Launcher.class);
    }
}
