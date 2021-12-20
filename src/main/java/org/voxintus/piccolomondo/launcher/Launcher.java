package org.voxintus.piccolomondo.launcher;

import java.io.File;
import org.apache.commons.lang3.SystemUtils;

public class Launcher {
    private static final String applicationName = "Piccolo Mondo";
    private static final String applicationVersion = "v0.1.0";
    private static final String applicationDirectory = "PiccoloMondo_v0";

    private static final String defaultApplicationPath = SystemUtils.USER_HOME + File.separator + applicationDirectory;
    private static final String defaultConfigurationFilename = "configuration.json";

    public static void main(String[] args) {
        System.out.println(applicationName + " " + applicationVersion + " launching ...");

        System.out.println("... running on : " + determineRunningOS());

        String applicationPath = defaultApplicationPath;
        String alternativeApplicationPath = System.getProperty("alternativeApplicationPath ");
        if (alternativeApplicationPath != null) {
            applicationPath = alternativeApplicationPath;
        }
        System.out.println("... using application directory : " + applicationPath);

        String configurationFilename = defaultConfigurationFilename;
        String alternativeConfigurationFilename = System.getProperty("alternativeConfigurationFilename");
        if (alternativeConfigurationFilename != null) {
            configurationFilename = alternativeConfigurationFilename;
        }
        System.out.println("... using configuration file : " + applicationPath + File.separator + configurationFilename);
    }

    private static String determineRunningOS() {
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
}
