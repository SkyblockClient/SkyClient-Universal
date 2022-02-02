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

public class LauncherSettings {
    private boolean crashAssistance;
    private boolean enableAdvanced;
    private boolean enableAnalytics;
    private boolean enableHistorical;
    private boolean enableReleases;
    private boolean enableSnapshots;
    private boolean keepLauncherOpen;
    private String profileSorting;
    private boolean showGameLog;
    private boolean showMenu;
    private boolean soundOn;

    @Override
    public String toString() {
        return "LauncherSettings(" +
                "crashAssistance=" + crashAssistance +
                ", enableAdvanced=" + enableAdvanced +
                ", enableAnalytics=" + enableAnalytics +
                ", enableHistorical=" + enableHistorical +
                ", enableReleases=" + enableReleases +
                ", enableSnapshots=" + enableSnapshots +
                ", keepLauncherOpen=" + keepLauncherOpen +
                ", profileSorting='" + profileSorting + '\'' +
                ", showGameLog=" + showGameLog +
                ", showMenu=" + showMenu +
                ", soundOn=" + soundOn +
                ')';
    }
}
