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

package io.github.koxx12dev.universal.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    public static String readFile(String filePath) {

        String content = "";

        try
        {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return content;
    }

    public static void writeFile(String content, File file) {

        //write the file
        try {
            Files.write(file.toPath(), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
