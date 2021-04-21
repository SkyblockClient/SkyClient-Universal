package io.github.koxx12_dev.skyclient_installer_macos;

public enum TerminalColors {
    HEADER("\033[95m"),
    OKBLUE("\033[94m"),
    OKCYAN("\033[96m"),
    OKGREEN("\033[92m"),
    WARNING("\033[93m"),
    FAIL("\033[91m"),
    ENDC("\033[0m"),
    BOLD("\033[1m"),
    UNDERLINE("\033[4m");

    private final String label;

    private TerminalColors(String value) {
        this.label = value;
    }

    public String getValue() {
        return label;
    }
}
