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
import io.github.koxx12dev.universal.gson.PropertiesData;
import io.github.koxx12dev.universal.guis.Installer;
import io.github.koxx12dev.universal.utils.FileUtil;
import io.github.koxx12dev.universal.utils.Http;
import io.github.koxx12dev.universal.utils.OSType;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

public class SkyclientUniversal {
    public static final Gson GSON = new Gson().newBuilder().setPrettyPrinting().create();
    public static BufferedImage skyclientIcon;
    public static PropertiesData propertiesData;
    public static ModData[] mods;
    public static PackData[] packs;
    public static final File CACHE = new File(
            System.getProperty("java.io.tmpdir")+
                    File.separator+
                    "skyclient-universal-cache"
    );
    static {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getResourceAsStream("/properties.json")))){
            propertiesData = GSON.fromJson(reader, PropertiesData.class);
            skyclientIcon = ImageIO.read(getResource("/universal/SkyblockClient128.png"));
            mods = GSON.fromJson(Http.get(propertiesData.baseRepo + "/files/mods.json"), ModData[].class);
            packs = GSON.fromJson(Http.get(propertiesData.baseRepo + "/files/packs.json"), PackData[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static LauncherProfiles launcherProfiles;
    public static File minecraft;
    public static File skyclient;

    public static void main(String[] args) throws IOException {
        setLookAndFeel();

        OSType type = OSType.getOS();
        if (type == null) {
            promptSelectMinecraftDirectory();
        }else {
            minecraft = OSType.getOS().getDefaultDirectory();
            if (!minecraft.exists() || !new File(minecraft, "launcher_profiles.json").exists()) {
                promptSelectMinecraftDirectory();
            }
        }
        skyclient = new File(minecraft, "skyclient");

        launcherProfiles = GSON.fromJson(FileUtil.readFile(minecraft.getAbsolutePath()+"/launcher_profiles.json"), LauncherProfiles.class);

        CACHE.mkdirs();

        Installer installer = new Installer();
        installer.initCategories();
        createPanels(installer);
        installer.init();

    }

    public static void promptSelectMinecraftDirectory() {

      int result =  JOptionPane.showConfirmDialog(null,
                "Unable to detect default minecraft directory\n" +
                        "click ok to choose manually or click cancel to close the installer",
                "Minecraft directory not found",
                JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
        //automatically focus the dialog
        JOptionPane.getRootFrame().requestFocusInWindow();
        //check what did the user choose
        if (result == JOptionPane.OK_OPTION) {
            minecraft = Installer.selectMinecraftDirectory();
        }else{
            System.exit(0);
        }
    }

    private static void createPanels(Installer installer) throws IOException {
        for (int i = 0; i < mods.length-1; i++) {
            installer.addModPanel(i, i-1);
        }
        for (int i = 0; i < packs.length-1; i++) {
            installer.addPackPanel(i, i-1);
        }
    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static @NonNull URL getResource(String path) {
        return Objects.requireNonNull(SkyclientUniversal.class.getResource(path));
    }
    private static @NonNull InputStream getResourceAsStream(String path) {
        return Objects.requireNonNull(SkyclientUniversal.class.getResourceAsStream(path));
    }

}
