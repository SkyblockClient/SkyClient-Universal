package io.github.koxx12dev.skyclientinstaller;

public enum LogType {
    INFO ("INFO"),
    WARNING ("WARNING"),
    ERROR ("ERROR"),
    FATAL ("FATAL");

    private final String name;

    LogType(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}
