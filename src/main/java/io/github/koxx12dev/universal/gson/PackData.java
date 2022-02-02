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

public class PackData {
    public String id;
    public String file;
    public String display;
    public String description;
    public String url;
    public String icon;
    public String icon_scaling;
    public String creator;
    public String discordcode;
    public String[] categories;
    public Actions[] actions;
    public boolean outdated;
    public boolean hidden;
    public boolean selected;
    public boolean enabled;

    public String getId() {
        return id;
    }

    public PackData setId(String id) {
        this.id = id;
        return this;
    }

    public String getFile() {
        return file;
    }

    public PackData setFile(String file) {
        this.file = file;
        return this;
    }

    public String getDisplay() {
        return display;
    }

    public PackData setDisplay(String display) {
        this.display = display;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PackData setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PackData setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public PackData setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getIcon_scaling() {
        return icon_scaling;
    }

    public PackData setIcon_scaling(String icon_scaling) {
        this.icon_scaling = icon_scaling;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public PackData setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getDiscordcode() {
        return discordcode;
    }

    public PackData setDiscordcode(String discordcode) {
        this.discordcode = discordcode;
        return this;
    }

    public String[] getCategories() {
        return categories;
    }

    public PackData setCategories(String[] categories) {
        this.categories = categories;
        return this;
    }

    public Actions[] getActions() {
        return actions;
    }

    public PackData setActions(Actions[] actions) {
        this.actions = actions;
        return this;
    }

    public boolean isOutdated() {
        return outdated;
    }

    public PackData setOutdated(boolean outdated) {
        this.outdated = outdated;
        return this;
    }

    public boolean isHidden() {
        return hidden;
    }

    public PackData setHidden(boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    @Override
    public String toString() {
        return "PackData(" +
                "id='" + id + '\'' +
                ", file='" + file + '\'' +
                ", display='" + display + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", icon_scaling='" + icon_scaling + '\'' +
                ", creator='" + creator + '\'' +
                ", discordcode='" + discordcode + '\'' +
                ", categories=" + Arrays.toString(categories) +
                ", actions=" + Arrays.toString(actions) +
                ", outdated=" + outdated +
                ", hidden=" + hidden +
                ')';
    }


}
