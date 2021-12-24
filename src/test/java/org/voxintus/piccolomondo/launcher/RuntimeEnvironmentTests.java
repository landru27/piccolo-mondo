/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ */

package org.voxintus.piccolomondo.launcher;

import java.util.regex.*;

import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RuntimeEnvironmentTests {
    @Test
    @DisplayName("detect running o/s")
    public void detectRunningOS() {

        switch (RuntimeEnvironment.detectRunningOS()) {
            case RUNTIME_OS_LINUX -> assertTrue(SystemUtils.IS_OS_LINUX);
            case RUNTIME_OS_MACOS -> assertTrue(SystemUtils.IS_OS_MAC_OSX);
            case RUNTIME_OS_XUNIX -> assertTrue(SystemUtils.IS_OS_UNIX);
            case RUNTIME_OS_MSWIN -> assertTrue(SystemUtils.IS_OS_WINDOWS);
            case RUNTIME_OS_OTHER -> fail();
        }
    }

    @Test
    @DisplayName("detect supported o/s")
    public void detectSupportedOS() {
        assertTrue(RuntimeEnvironment.isSupportedOS(
            RuntimeEnvironment.detectRunningOS())
        );
    }

    @Test
    @DisplayName("detect o/s description")
    public void detectOSDescription() {
        String osDescription = RuntimeEnvironment.reportRunningOS(
            RuntimeEnvironment.detectRunningOS()
        );

        String regexp = "^(\\w+ ){1,2}\\(.+, .+, .+\\)$";
        System.out.println(osDescription);
        System.out.println(regexp);

        boolean found = Pattern.compile(regexp).matcher(osDescription).find();
        assertTrue(found);
    }

}
