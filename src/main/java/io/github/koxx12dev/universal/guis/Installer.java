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

package io.github.koxx12dev.universal.guis;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import io.github.koxx12dev.universal.SkyclientUniversal;
import io.github.koxx12dev.universal.gson.Actions;
import io.github.koxx12dev.universal.gson.MinecraftProfile;
import io.github.koxx12dev.universal.gson.ModData;
import io.github.koxx12dev.universal.gson.PackData;
import io.github.koxx12dev.universal.utils.FileUtil;
import io.github.koxx12dev.universal.utils.Http;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

public class Installer {
    public JPanel ModPane;
    public JPanel PackPane;
    public JPanel pane;
    private JButton InstallButton;
    private JScrollPane ModScrollPane;
    private JScrollPane PackScrollPane;
    private JFrame frame;

    public Installer() {
        InstallButton.addActionListener(e -> {
            //run code that is bellow this command without freezing main ui
            new Thread(() -> {
                InstallButton.setEnabled(false);

                new File(SkyclientUniversal.skyclient, "mods").mkdirs();
                new File(SkyclientUniversal.skyclient, "resourcepacks").mkdirs();

                int selectedPacks = Arrays.stream(SkyclientUniversal.packs).filter(pack -> pack.selected).mapToInt(pack -> 1).sum();
                int selectedMods = Arrays.stream(SkyclientUniversal.mods).filter(mod -> mod.selected).mapToInt(mod -> 1).sum();

                System.out.println("Selected Packs: " + selectedPacks);
                System.out.println("Selected Mods: " + selectedMods);

                if (selectedMods > 0) {
                    Loading modLoading = new Loading("Downloading Mods", "Starting Download", selectedMods);

                    try {
                        Thread.sleep(1000); //makes sure that loading bar works correctly
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }


                    for (ModData mod : SkyclientUniversal.mods) {
                        if (mod.selected) {

                            try {
                                modLoading.nextStep("Downloading " + mod.getDisplay());
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }

                            System.out.println("Downloading " + mod.getDisplay());
                            if (mod.url != null) {
                                Http.download(mod.url, SkyclientUniversal.skyclient + "/mods/" + mod.file);
                            } else if (!mod.id.equals("minecraft")) {
                                Http.download(SkyclientUniversal.baseRepo + "/files/mods/" + mod.file, SkyclientUniversal.skyclient + "/mods/" + mod.file);
                            }
                            if (mod.hash != null) {
                                try {
                                    String hash = Hashing.sha256().hashBytes(Files.toByteArray(new File(SkyclientUniversal.skyclient + "/mods/" + mod.file))).toString();
                                    if (!hash.equals(mod.hash)) {
                                        System.out.println("Hash of " + mod.getDisplay() + " is incorrect, deleting and downloading again");
                                        new File(SkyclientUniversal.skyclient + "/mods/" + mod.file).delete();
                                        if (mod.url != null) {
                                            Http.download(mod.url, SkyclientUniversal.skyclient + "/mods/" + mod.file);
                                        } else if (!mod.id.equals("minecraft")) {
                                            Http.download(SkyclientUniversal.baseRepo + "/files/mods/" + mod.file, SkyclientUniversal.skyclient + "/mods/" + mod.file);
                                        }
                                    } else {
                                        System.out.println("Hash of " + mod.getDisplay() + " is correct");
                                    }
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            }
                            if (mod.files != null) {
                                for (String file : mod.files) {
                                    try {
                                        System.out.println("Downloading " + mod.getDisplay() + " " + file);
                                        File file1 = new File(SkyclientUniversal.skyclient, file);

                                        file1.getParentFile().mkdirs();
                                        Http.download(SkyclientUniversal.baseRepo + "/files/mcdir/" + file, file1.getAbsolutePath());

                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }

                            if (mod.packages != null) {
                                for (String mod1 : mod.packages) {
                                    //get the modData from the mod id using stream
                                    ModData mod2 = Arrays.stream(SkyclientUniversal.mods).filter(m -> m.id.equals(mod1)).findFirst().orElse(null);
                                    if (mod2 != null) {
                                        System.out.println("Downloading " + mod2.getDisplay());
                                        if (mod2.url != null) {
                                            Http.download(mod2.url, SkyclientUniversal.skyclient + "/mods/" + mod2.file);
                                        } else if (!mod2.id.equals("minecraft")) {
                                            Http.download(SkyclientUniversal.baseRepo + "/files/mods/" + mod2.file, SkyclientUniversal.skyclient + "/mods/" + mod2.file);
                                        }
                                        if (mod2.hash != null) {
                                            try {
                                                String hash = Hashing.sha256().hashBytes(Files.toByteArray(new File(SkyclientUniversal.skyclient + "/mods/" + mod2.file))).toString();
                                                if (!hash.equals(mod2.hash)) {
                                                    System.out.println("Hash of " + mod2.getDisplay() + " is incorrect, deleting and downloading again");
                                                    new File(SkyclientUniversal.skyclient + "/mods/" + mod2.file).delete();
                                                    if (mod2.url != null) {
                                                        Http.download(mod2.url, SkyclientUniversal.skyclient + "/mods/" + mod2.file);
                                                    } else if (!mod2.id.equals("minecraft")) {
                                                        Http.download(SkyclientUniversal.baseRepo + "/files/mods/" + mod2.file, SkyclientUniversal.skyclient + "/mods/" + mod2.file);
                                                    }
                                                } else {
                                                    System.out.println("Hash of " + mod2.getDisplay() + " is correct");
                                                }
                                            } catch (Exception e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                        if (mod2.files != null) {
                                            for (String file : mod2.files) {
                                                try {
                                                    System.out.println("Downloading " + mod2.getDisplay() + " " + file);
                                                    File file1 = new File(SkyclientUniversal.skyclient, file);

                                                    file1.getParentFile().mkdirs();
                                                    Http.download(SkyclientUniversal.baseRepo + "/files/mcdir/" + file, file1.getAbsolutePath());

                                                } catch (Exception e1) {
                                                    e1.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    modLoading.pop();
                }

                if (selectedPacks > 0) {
                    Loading packLoading = new Loading("Downloading Packs", "Starting Download", selectedPacks);

                    for (PackData pack : SkyclientUniversal.packs) {
                        if (pack.selected) {
                            try {
                                Thread.sleep(1000); // makes sure that loading bar works correctly
                                packLoading.nextStep("Downloading " + pack.getDisplay());
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            System.out.println("Downloading " + pack.getDisplay());
                            if (pack.url != null) {
                                Http.download(pack.url, SkyclientUniversal.skyclient + "/resourcepacks/" + pack.file);
                            } else {
                                Http.download(SkyclientUniversal.baseRepo + "/files/packs/" + Http.urlEscape(pack.file), SkyclientUniversal.skyclient + "/resourcepacks/" + pack.file);
                            }
                        }
                    }

                    packLoading.pop();
                }

                if (!SkyclientUniversal.launcherProfiles.profiles.containsKey("SkyClient")) {
                    MinecraftProfile profile = new MinecraftProfile();
                    try {
                        profile.gameDir = SkyclientUniversal.skyclient.getCanonicalPath();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    profile.icon = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAMAAAD04JH5AAAACGFjVEwAAAAEAAAAAHzNZtAAAAAzUExURf//ACOQY5HbaR68c////11dXXR0dIWFhc3fbNDq6T09PWG/5j+Y2KjQ2Sdyxtrq+k2b5npKrGcAAAABdFJOUwBA5thmAAAAGmZjVEwAAAAAAAAAgAAAAIAAAAAAAAAAAAAIAGQAAK+px9AAAAKZSURBVHja7ZvpbgIxDISBQrcFerz/0zYjZarp4F0OocYgz5898PEhWXESwmp1Rpuu1SgVQAqAl6Z7QNwUowCGAjA5tO6as/1o2nbN2cD/KogCSAHw1kSAyBkJ37u2IrdjISNeATwGAI0pFqFCeFJ9xr0mp28BPAaAFgyTR4ORJ8RgxIL0ePBjvALID8CEdGBTigqRTQjXpYZGv4uaUQEMBWASB4Cu+hZN9Hf4Rf8CGArAAUgHjY2In9Pm0ma2NhVAXgBNxORaOFpMDqBBdSLDGK4CyAegAw+LzZuIBtOEXMDqQiYqQC3yE4gCGA5AZx94IgB/1k0MHbAiAIUogDwAPvlgIUUTCW9IGlQ3tebswkIsgKEAvhDVhYgaewKfYCxBRfEKIDdAFMATfDYduw5NHlhjaAG6TQGMB9Aiigowak6HrqMIQOcAfGJaADkA9Mckbxo6yXBnbEyxALExOTW9NvkEdG5h8pu8AFIAvHbtm2iw69JnJCEUN6gBgPupC3G0APmecXgPuwLIB4AkgNBgfO8AKED+YIFBaBLBZjIhDu8RqwByAfAFr2qsACww3HMQggBAnyiGxqHNn5VxAQwHIAQKUAtPA3hgAqAIcaWv20UxCyA3AAPwfglAC3HfpXb6pXaicKe8AIYDQAxARzpPgTQ5RH9PHsUsgLwACqEAPpnAFQ2IC1QCeGNjMj/oUgC5AQhBZwb0SaoCQJE/Dzb5gTc/6FQAuQD8gJo2kQiAEEsHnVVnD0AXwFCAr6bogOrOhALTRhQBePF5zALIB6AfRgOIN6nvprmByAuRg4/GPWlKBTAcwJPqM5uHTjKRlBtUOiG5WQUwHEAhzk0cdZOqAJ4L4BqxOAGw9Ae3AnhOgGiy8a8QBXBPgB9G8drRaA0Z2QAAABpmY1RMAAAAAQAAABQAAAA8AAAANAAAADwACABkAAETQ/wcAAAAWmZkQVQAAAACeNq1kzEOACAIAx1cjMT/P9eSSCLqJOU2joVAKQOUk6BUOmBLRUAFLLkX2mTIbNg3u7ZEkExssS4UQZmZ1WcQPmXGf9rAbvCgNCELlrRwNRCUEzPhDNF8OvhOAAAAGmZjVEwAAAADAAAAFAAAAEgAAAA4AAAAOAAIAGQCAb6WRGwAAABrZmRBVAAAAAR42s2TQQ6AIAwEOXgxEv//XEvCmmK97Zg4F8L0AC1L60F78kN5BtuEkkewCEB+0f8elKIpx+W1klKFMXBCitdBm5JEQ1ADhMxBKwVTkpTHAmQOGCVzYJdDAamA3Z/NlGXjyQth3xPxgCBbFQAAABpmY1RMAAAABQAAABQAAABEAAAANAAAADwACABkAAHaykukAAAAc2ZkQVQAAAAGeNq1lDEKwDAMAz10KS39/3MrDxIu2SrllphzII5xUjcocAKtpmRwgN5wgYR8QMuaBCQPUPGmZOEdq3hTdsBmMJmQJCl5CTXZlDtYGhyWSphykpI9tEvWlDsKnoP7OfSn5FDNB+bKiT4AT76lBBmhtuxsJwAAABh0RVh0U29mdHdhcmUAZ2lmMmFwbmcuc2YubmV0lv8TyAAAAABJRU5ErkJggg==";
                    profile.javaArgs = "-Xmx3G -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M";
                    profile.lastUsed = "2021-08-26T07:00:00.642Z";
                    profile.lastVersionId = "1.8.9-forge1.8.9-11.15.1.2318-1.8.9";
                    profile.name = "SkyClient";
                    profile.type = "custom";

                    SkyclientUniversal.launcherProfiles.profiles.put("SkyClient", profile);

                    FileUtil.writeFile(SkyclientUniversal.gson.toJson(SkyclientUniversal.launcherProfiles), new File(SkyclientUniversal.minecraft, "launcher_profiles.json"));
                }

                File forgeLib = new File(SkyclientUniversal.minecraft, "/libraries/net/minecraftforge/forge/1.8.9-11.15.1.2318-1.8.9/forge-1.8.9-11.15.1.2318-1.8.9.jar");

                if (!forgeLib.exists()) {
                    File forgeJson = new File(SkyclientUniversal.minecraft, "/versions/1.8.9-forge1.8.9-11.15.1.2318-1.8.9/1.8.9-forge1.8.9-11.15.1.2318-1.8.9.json");

                    forgeLib.getParentFile().mkdirs();
                    Http.download("https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/forge/forge-1.8.9-11.15.1.2318-1.8.9.jar", forgeLib.getAbsolutePath());

                    forgeJson.getParentFile().mkdirs();
                    Http.download("https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/forge/1.8.9-forge1.8.9-11.15.1.2318-1.8.9.json", forgeJson.getAbsolutePath());
                }

                //show a installation complete popup with installation details
                JOptionPane.showMessageDialog(frame, "Installed Skyclient!", "Complete", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);

            }).start();
        });
    }

    //https://stackoverflow.com/questions/14548808/scale-the-imageicon-automatically-to-label-size
    public static BufferedImage resize(BufferedImage image, int width, int height, boolean pixelated) {
        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        if (pixelated) {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        } else {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        }
        g2.drawImage(image, 0, 0, width, height, null);
        g2.dispose();
        return resizedImg;
    }

    public void addModPanel(int modPos, int pos) throws IOException {
        pos = pos + 1;
        ModData mod = SkyclientUniversal.mods[modPos];
        if (!mod.hidden) {
            GridBagConstraints gbc;
            JPanel DataPanel = new JPanel();
            DataPanel.setLayout(new GridBagLayout());
            DataPanel.setDoubleBuffered(true);
            DataPanel.setEnabled(true);
            DataPanel.setOpaque(true);
            DataPanel.setPreferredSize(new Dimension(250, 67));
            DataPanel.setRequestFocusEnabled(true);
            DataPanel.setVerifyInputWhenFocusTarget(true);
            DataPanel.setVisible(true);
            gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            JLabel Name = new JLabel();
            Name.setHorizontalAlignment(SwingConstants.CENTER);
            Name.setVerticalAlignment(SwingConstants.CENTER);
            Name.setSize(new Dimension(150, 67));
            Name.setMaximumSize(new Dimension(150, 67));
            Name.setPreferredSize(new Dimension(150, 67));
            Name.setMinimumSize(new Dimension(150, 67));
            Name.setText(mod.getDisplay());
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            DataPanel.add(Name, gbc);
            JLabel Image = new JLabel();
            Image.setHorizontalAlignment(SwingConstants.LEFT);
            Image.setHorizontalTextPosition(SwingConstants.TRAILING);
            BufferedImage icon;
            if (mod.icon != null) {
                if (new File(SkyclientUniversal.cache, mod.getIcon()).exists()) {
                    icon = resize(ImageIO.read(new File(SkyclientUniversal.cache, mod.getIcon())), 50, 50, Objects.equals(mod.icon_scaling, "pixel"));
                } else {
                    try {
                        icon = resize(ImageIO.read(Objects.requireNonNull(SkyclientUniversal.class.getResource("universal/SkyblockClient128.png"))), 64, 64, Objects.equals(mod.icon_scaling, "pixel"));
                    } catch (Exception e) {
                        icon = resize(ImageIO.read(new URL(SkyclientUniversal.baseCdn + "/files/icons/skyclient.png")), 64, 64, Objects.equals(mod.icon_scaling, "pixel"));
                    }
                    Thread t = new Thread(() -> {
                        BufferedImage icon2;
                        try {
                            System.out.println("Downloading Icon for " + mod.getDisplay());
                            Http.download(SkyclientUniversal.baseCdn + "/files/icons/" + mod.getIcon(), SkyclientUniversal.cache + "\\" + mod.getIcon());
                            icon2 = resize(ImageIO.read(new File(SkyclientUniversal.cache, mod.getIcon())), 64, 64, Objects.equals(mod.icon_scaling, "pixel"));
                            Image.setIcon(new ImageIcon(icon2));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    t.start();
                }
            } else {
                icon = resize(SkyclientUniversal.skyclientIcon, 64, 64, false);
            }
            Image.setIcon(new ImageIcon(icon));
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            DataPanel.add(Image, gbc);
            JCheckBox Enabled = new JCheckBox();
            Enabled.setText("");
            Enabled.setSelected(mod.enabled);
            SkyclientUniversal.mods[modPos].selected = Enabled.isSelected();
            Enabled.addActionListener(e -> {

                if (mod.warning != null && Enabled.isSelected()) {

                    StringBuilder warning = new StringBuilder();
                    for (String line : mod.warning.lines) {
                        warning.append(line).append("\n");
                    }
                    warning.deleteCharAt(warning.length() - 1);
                    int dialog = JOptionPane.showConfirmDialog(null, warning.toString(), "Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

                    Enabled.setSelected(dialog == JOptionPane.YES_OPTION);

                }

                SkyclientUniversal.mods[modPos].selected = Enabled.isSelected();
                System.out.println(mod.getId() + " is now " + Enabled.isSelected());
            });
            gbc = new GridBagConstraints();
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            DataPanel.add(Enabled, gbc);
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = pos;

            if (mod.actions != null) {
                DataPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            JPopupMenu popup = new JPopupMenu();
                            for (Actions action : mod.actions) {
                                JMenuItem item = null;
                                if (!Objects.equals(action.method, "hover")) {
                                    item = new JMenuItem(action.text);
                                    item.addActionListener(e1 -> {
                                        try {
                                            Desktop.getDesktop().browse(new URL(action.link).toURI());
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                        }
                                    });
                                }
                                if (item != null) {
                                    popup.add(item);
                                }
                            }
                            popup.show(e.getComponent(), e.getX(), e.getY());
                        }
                    }
                });
            }

            ModPane.add(DataPanel, gbc);
        }


    }

    public void addPackPanel(int packPos, int pos) throws IOException {
        pos = pos + 1;
        PackData pack = SkyclientUniversal.packs[packPos];
        if (!pack.hidden) {
            GridBagConstraints gbc;
            JPanel DataPanel = new JPanel();
            DataPanel.setLayout(new GridBagLayout());
            DataPanel.setDoubleBuffered(true);
            DataPanel.setEnabled(true);
            DataPanel.setOpaque(true);
            DataPanel.setPreferredSize(new Dimension(250, 67));
            DataPanel.setRequestFocusEnabled(true);
            DataPanel.setVerifyInputWhenFocusTarget(true);
            DataPanel.setVisible(true);
            gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            JLabel Name = new JLabel();
            Name.setHorizontalAlignment(SwingConstants.CENTER);
            Name.setVerticalAlignment(SwingConstants.CENTER);
            Name.setSize(new Dimension(150, 67));
            Name.setMaximumSize(new Dimension(150, 67));
            Name.setPreferredSize(new Dimension(150, 67));
            Name.setMinimumSize(new Dimension(150, 67));
            Name.setText(pack.getDisplay());
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            DataPanel.add(Name, gbc);
            JLabel Image = new JLabel();
            Image.setHorizontalAlignment(SwingConstants.LEFT);
            Image.setHorizontalTextPosition(SwingConstants.TRAILING);
            BufferedImage icon;
            if (new File(SkyclientUniversal.cache, pack.getIcon()).exists()) {
                icon = resize(ImageIO.read(new File(SkyclientUniversal.cache, pack.getIcon())), 50, 50, Objects.equals(pack.icon_scaling, "pixel"));
            } else {
                try {
                    icon = resize(ImageIO.read(Objects.requireNonNull(SkyclientUniversal.class.getResource("universal/SkyblockClient128.png"))), 64, 64, Objects.equals(pack.icon_scaling, "pixel"));
                } catch (Exception e) {
                    icon = resize(ImageIO.read(new URL(SkyclientUniversal.baseCdn + "/files/icons/skyclient.png")), 64, 64, Objects.equals(pack.icon_scaling, "pixel"));
                }
                Thread t = new Thread(() -> {
                    BufferedImage icon2;
                    try {
                        System.out.println("Downloading Icon for " + pack.getDisplay());
                        Http.download(SkyclientUniversal.baseCdn + "/files/icons/" + pack.getIcon(), SkyclientUniversal.cache + "\\" + pack.getIcon());
                        icon2 = resize(ImageIO.read(new File(SkyclientUniversal.cache, pack.getIcon())), 64, 64, Objects.equals(pack.icon_scaling, "pixel"));
                        Image.setIcon(new ImageIcon(icon2));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                t.start();
            }
            Image.setIcon(new ImageIcon(icon));
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            DataPanel.add(Image, gbc);
            JCheckBox Enabled = new JCheckBox();
            Enabled.setText("");
            Enabled.setSelected(pack.enabled);
            SkyclientUniversal.packs[packPos].selected = Enabled.isSelected();
            Enabled.addActionListener(e -> {
                SkyclientUniversal.packs[packPos].selected = Enabled.isSelected();
                System.out.println(pack.getId() + " is now " + Enabled.isSelected());
            });
            gbc = new GridBagConstraints();
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            DataPanel.add(Enabled, gbc);
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = pos;

            if (pack.actions != null) {
                DataPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            JPopupMenu popup = new JPopupMenu();
                            for (Actions action : pack.actions) {
                                JMenuItem item = null;
                                if (!Objects.equals(action.method, "hover")) {
                                    item = new JMenuItem(action.text);
                                    item.addActionListener(e1 -> {
                                        try {
                                            Desktop.getDesktop().browse(new URL(action.link).toURI());
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                        }
                                    });
                                }
                                if (item != null) {
                                    popup.add(item);
                                }
                            }
                            popup.show(e.getComponent(), e.getX(), e.getY());
                        }
                    }
                });
            }

            PackPane.add(DataPanel, gbc);
        }
    }

    public void init() {
        ModScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        PackScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        frame = new JFrame("Skyclient Universal Installer - " + SkyclientUniversal.version);
        frame.setContentPane(pane);
        frame.setResizable(false);
        frame.setIconImage(SkyclientUniversal.skyclientIcon);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        ModScrollPane = new JScrollPane();
        ModScrollPane.setDoubleBuffered(false);
        ModScrollPane.setMinimumSize(new Dimension(250, 300));
        ModScrollPane.setPreferredSize(new Dimension(270, 360));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(1, 1, 1, 1);
        pane.add(ModScrollPane, gbc);
        ModPane = new JPanel();
        ModPane.setLayout(new GridBagLayout());
        ModScrollPane.setViewportView(ModPane);
        PackScrollPane = new JScrollPane();
        PackScrollPane.setDoubleBuffered(false);
        PackScrollPane.setMinimumSize(new Dimension(250, 300));
        PackScrollPane.setPreferredSize(new Dimension(270, 360));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(1, 1, 1, 1);
        pane.add(PackScrollPane, gbc);
        PackPane = new JPanel();
        PackPane.setLayout(new GridBagLayout());
        PackScrollPane.setViewportView(PackPane);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        pane.add(spacer1, gbc);
        InstallButton = new JButton();
        InstallButton.setMaximumSize(new Dimension(100, 30));
        InstallButton.setMinimumSize(new Dimension(100, 30));
        InstallButton.setPreferredSize(new Dimension(100, 30));
        InstallButton.setText("Install");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pane.add(InstallButton, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pane.add(spacer2, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return pane;
    }

}
