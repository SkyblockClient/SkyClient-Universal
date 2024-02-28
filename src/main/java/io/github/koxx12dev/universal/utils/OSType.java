package io.github.koxx12dev.universal.utils;

import java.io.File;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public enum OSType {
    WINDOWS("/AppData/Roaming/.minecraft"),
    MAC("/Library/Application Support/minecraft"),
    LINUX("/.minecraft"),
    ;

    private final String path;

    OSType(String path) {
        this.path = path;
    }

    public File getDefaultDirectory() {
       return new File(System.getProperty("user.home") + path);
    }

    public static OSType getOS() {
        for (OSType value : values()) {
            if (System.getProperty("os.name").toLowerCase().contains(value.name().toLowerCase())) {
                return value;
            }
        }
        return null;
    }

}
