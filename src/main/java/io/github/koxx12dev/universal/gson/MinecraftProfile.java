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

public class MinecraftProfile {
    public String gameDir;
    public String icon;
    public String javaArgs;
    public String lastUsed;
    public String lastVersionId;
    public String name;
    public String type;

    public MinecraftProfile(String gameDir, String icon, String javaArgs, String lastUsed, String lastVersionId, String name, String type) {
        this.gameDir = gameDir;
        this.icon = icon;
        this.javaArgs = javaArgs;
        this.lastUsed = lastUsed;
        this.lastVersionId = lastVersionId;
        this.name = name;
        this.type = type;
    }

    public MinecraftProfile() {

    }

    @Override
    public String toString() {
        return "MinecraftProfile(" +
                "gameDir='" + gameDir + '\'' +
                ", icon='" + icon + '\'' +
                ", javaArgs='" + javaArgs + '\'' +
                ", lastUsed='" + lastUsed + '\'' +
                ", lastVersionId='" + lastVersionId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ')';
    }
}
