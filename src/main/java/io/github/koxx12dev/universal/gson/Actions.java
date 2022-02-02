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

public class Actions {
    public String method;
    public String document;
    public String icon;
    public String text;
    public String creator;
    public String link;

    public String getMethod() {
        return method;
    }

    public Actions setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getDocument() {
        return document;
    }

    public Actions setDocument(String document) {
        this.document = document;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Actions setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getText() {
        return text;
    }

    public Actions setText(String text) {
        this.text = text;
        return this;
    }

    public String getLink() {
        return link;
    }

    public Actions setLink(String link) {
        this.link = link;
        return this;
    }

    @Override
    public String toString() {
        return "Actions(" +
                "method='" + method + '\'' +
                ", document='" + document + '\'' +
                ", icon='" + icon + '\'' +
                ", text='" + text + '\'' +
                ", link='" + link + '\'' +
                ')';
    }
}
