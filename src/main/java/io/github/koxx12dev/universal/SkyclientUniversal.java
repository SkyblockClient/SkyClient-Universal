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

package io.github.koxx12dev.universal;

import com.formdev.flatlaf.FlatDarkLaf;
import com.google.gson.Gson;
import io.github.koxx12dev.universal.gson.LauncherProfiles;
import io.github.koxx12dev.universal.gson.ModData;
import io.github.koxx12dev.universal.gson.PackData;
import io.github.koxx12dev.universal.guis.Installer;
import io.github.koxx12dev.universal.utils.FileUtil;
import io.github.koxx12dev.universal.utils.Http;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SkyclientUniversal {

    public static BufferedImage skyclientIcon;

    static {
        try {
            skyclientIcon = ImageIO.read(SkyclientUniversal.class.getResource("/universal/SkyblockClient128.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String version = "2.0.4";
    public static Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
    public static String baseRepo = "https://raw.githubusercontent.com/SkyblockClient/SkyblockClient-REPO/main";
    public static String baseCdn = "https://cdn.jsdelivr.net/gh/SkyblockClient/SkyblockClient-REPO@main";
    public static ModData[] mods = gson.fromJson(Http.get(baseRepo+"/files/mods.json"), ModData[].class);
    public static PackData[] packs = gson.fromJson(Http.get(baseRepo+"/files/packs.json"), PackData[].class);
    public static File cache = new File(
        System.getProperty("java.io.tmpdir")+
        File.separator+
        "skyclient-universal-cache"
    );
    public static LauncherProfiles launcherProfiles;
    public static File minecraft;
    public static File skyclient;

    public static void main(String[] args) throws IOException {

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(System.getProperty("os.name").toLowerCase().contains("mac")){
            minecraft = new File(System.getProperty("user.home")+"/Library/Application Support/minecraft");
        } else if(System.getProperty("os.name").toLowerCase().contains("linux")){
            minecraft = new File(System.getProperty("user.home")+"/.minecraft");
        }
        else if(System.getProperty("os.name").toLowerCase().contains("windows")){
            minecraft = new File(System.getProperty("user.home")+"/AppData/Roaming/.minecraft");
        }
        else {
            JOptionPane.showMessageDialog(null, "Could not automatically determine the location of your .minecraft directory.\nClick ok to select it yourself.", "Could not find .minecraft directory", JOptionPane.WARNING_MESSAGE);
            minecraft = Installer.selectMinecraftDirectory();
        }

        // Keep trying until we have a valid file path
        while (!minecraft.exists() || !(new File(minecraft, "launcher_profiles.json").exists())) {
            JOptionPane.showMessageDialog(null, "The .minecraft directory does not exist or does not have a launcher_profiles.json inside of it.\nClick ok to select a .minecraft yourself.", "Invalid .minecraft directory", JOptionPane.WARNING_MESSAGE);
           minecraft = Installer.selectMinecraftDirectory();
        }

        skyclient = new File(minecraft, "skyclient");

        launcherProfiles = gson.fromJson(FileUtil.readFile(minecraft.getAbsolutePath()+"/launcher_profiles.json"), LauncherProfiles.class);

        cache.mkdirs();

        Installer installer = new Installer();

        installer.initCategories();

        for (int i = 0; i < mods.length-1; i++) {
            installer.addModPanel(i, i-1);
        }
        for (int i = 0; i < packs.length-1; i++) {
            installer.addPackPanel(i, i-1);
        }
        installer.init();

    }

}
