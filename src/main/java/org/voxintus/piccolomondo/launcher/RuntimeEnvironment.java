/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ */

package org.voxintus.piccolomondo.launcher;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.SystemUtils;

public final class RuntimeEnvironment {

    // RuntimeEnvironment is a static class
    private void RuntimeEnvironment() { }

    public static RuntimeOS detectRunningOS() {
        if (SystemUtils.IS_OS_LINUX) {
            return RuntimeOS.RUNTIME_OS_LINUX;
        }
        if (SystemUtils.IS_OS_MAC_OSX) {
            return RuntimeOS.RUNTIME_OS_MACOS;
        }
        if (SystemUtils.IS_OS_UNIX) {
            return RuntimeOS.RUNTIME_OS_XUNIX;
        }
        if (SystemUtils.IS_OS_WINDOWS) {
            return RuntimeOS.RUNTIME_OS_MSWIN;
        }

        return RuntimeOS.RUNTIME_OS_OTHER;
    }

    public static String reportRunningOS(RuntimeOS os) {
        String osDescription = SystemUtils.OS_NAME + ", " + SystemUtils.OS_VERSION + ", " + SystemUtils.OS_ARCH;

        String shortName = switch (os) {
            case RUNTIME_OS_LINUX -> "Linux";
            case RUNTIME_OS_MACOS -> "MacOS";
            case RUNTIME_OS_XUNIX -> "UNIX";
            case RUNTIME_OS_MSWIN -> "Microsoft Windows";
            case RUNTIME_OS_OTHER -> "UNRECOGNIZED";
        };

        return shortName + " (" + osDescription + ")";
    }

    public static Boolean isSupportedOS(RuntimeOS os) {

        return switch (os) {
            case RUNTIME_OS_LINUX,
                 RUNTIME_OS_MACOS,
                 RUNTIME_OS_XUNIX,
                 RUNTIME_OS_MSWIN -> true;
            case RUNTIME_OS_OTHER -> false;
        };
    }

    public static String getCurrentFullDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
