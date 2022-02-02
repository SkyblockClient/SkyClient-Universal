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

import java.util.Arrays;

public class ModData {
    public String id;
    public String[] nicknames;
    public boolean enabled;
    public String file;
    public String url;
    public String display;
    public String description;
    public String icon;
    public String icon_scaling;
    public String creator;
    public String discordcode;
    public Actions[] actions;
    public String[] categories;
    public String[] files;
    public boolean hidden;
    public boolean selected;
    public String hash;
    public String[] packages;
    public Warning warning;


    public String getId() {
        return id;
    }

    public ModData setId(String id) {
        this.id = id;
        return this;
    }

    public String[] getNicknames() {
        return nicknames;
    }

    public ModData setNicknames(String[] nicknames) {
        this.nicknames = nicknames;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public ModData setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getFile() {
        return file;
    }

    public ModData setFile(String file) {
        this.file = file;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ModData setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDisplay() {
        return display;
    }

    public ModData setDisplay(String display) {
        this.display = display;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ModData setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public ModData setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getIcon_scaling() {
        return icon_scaling;
    }

    public ModData setIcon_scaling(String icon_scaling) {
        this.icon_scaling = icon_scaling;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public ModData setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getDiscordcode() {
        return discordcode;
    }

    public ModData setDiscordcode(String discordcode) {
        this.discordcode = discordcode;
        return this;
    }

    public Actions[] getActions() {
        return actions;
    }

    public ModData setActions(Actions[] actions) {
        this.actions = actions;
        return this;
    }

    public String[] getCategories() {
        return categories;
    }

    public ModData setCategories(String[] categories) {
        this.categories = categories;
        return this;
    }

    public String[] getFiles() {
        return files;
    }

    public ModData setFiles(String[] files) {
        this.files = files;
        return this;
    }

    @Override
    public String toString() {
        return "ModData(" +
                "id='" + id + '\'' +
                ", nicknames=" + Arrays.toString(nicknames) +
                ", enabled=" + enabled +
                ", file='" + file + '\'' +
                ", url='" + url + '\'' +
                ", display='" + display + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", icon_scaling='" + icon_scaling + '\'' +
                ", creator='" + creator + '\'' +
                ", discordcode='" + discordcode + '\'' +
                ", actions=" + Arrays.toString(actions) +
                ", categories=" + Arrays.toString(categories) +
                ", files=" + Arrays.toString(files) +
                ')';
    }

    public class Warning {

        public String[] lines;

    }

}
