/*
 * Skyclient Universal Installer - Skyclient installer but written in java!
 * Copyright (C) koxx12-dev [2021 - 2022]
 *
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found under this url
 * https://github.com/koxx12-dev/Skyclient-installer-Java
 *
 * If you have a private concern, please contact me on
 * Discord: Koxx12#8061
 *
 */

package io.github.koxx12dev.universal.gson;

import java.util.Map;

public class LauncherProfiles {
    public Map<String,MinecraftProfile> profiles;
    public LauncherSettings settings;
    public int version;
    public String selectedProfile;

    @Override
    public String toString() {
        return "LauncherProfiles(" +
                "profiles=" + profiles +
                ", settings=" + settings +
                ", version=" + version +
                ", selectedProfile='" + selectedProfile + '\'' +
                ')';
    }
}
