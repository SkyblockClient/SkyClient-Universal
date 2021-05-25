package io.github.koxx12_dev.skyclient_installer_java;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import org.apache.commons.lang.SystemUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class MainGui extends Utils {

    public static void main(String[] args) throws IOException, URISyntaxException {

        if (!System.getProperty("java.version").startsWith("1.8")) {
            JOptionPane.showMessageDialog(null, "Ur using a wrong version of java\nu be using should 1.8 not "+System.getProperty("java.version"), "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        LafManager.enableLogging(false);
        LafManager.install(new DarculaTheme());

        UpdateCheck();

        //createLogFile();

        List<String> displayed = new ArrayList<>();

        String modsrq = request("https://raw.githubusercontent.com/nacrt/SkyblockClient-REPO/main/files/mods.json");
        String packsrq = request("https://raw.githubusercontent.com/nacrt/SkyblockClient-REPO/main/files/packs.json");

        JSONArray packsjson = new JSONArray(packsrq);
        JSONArray modsjson = new JSONArray(modsrq);

        modsjson.remove(modsjson.length() - 1);
        String os = "";

        if (SystemUtils.IS_OS_WINDOWS) {
            os = "win";
        } else if (SystemUtils.IS_OS_MAC) {
            os = "mac";
        } else if (SystemUtils.IS_OS_LINUX) {
            os = "linux";
        } else {
            JOptionPane.showMessageDialog(null, "Looks like ur os isnt supported", "L", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        for (int i = 0; i < modsjson.length(); i++) {
            try {

                Boolean temp = (Boolean) modsjson.getJSONObject(i).get("hidden");

            } catch (Exception e) {
                displayed.add((String) modsjson.getJSONObject(i).get("display"));
            }


        }

        sendLog(displayed+"",MainGui.class,LogType.INFO);

        GuiInit(displayed, modsjson, packsjson);
    }

    public static void GuiInit(List<String> list, JSONArray modsjson, JSONArray packsjson) throws IOException {
        JFrame frame = new JFrame("Skyclient Installer");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(new URL("https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/config/icon.png")));
        frame.setResizable(false);
        Gui(frame.getContentPane(), list, modsjson, packsjson);
        frame.pack();
        frame.setVisible(true);
    }

    public static void Gui(Container truepane, List<String> list, JSONArray modsjson, JSONArray packsjson) throws MalformedURLException {

        String mc = "";

        if (SystemUtils.IS_OS_WINDOWS) {
            mc = System.getenv("APPDATA") + "\\.minecraft";
        } else if (SystemUtils.IS_OS_MAC) {
            mc = System.getenv("HOME") + "/Library/Application Support/minecraft";
        } else if (SystemUtils.IS_OS_LINUX) {
            mc = System.getenv("HOME") + "/.minecraft";
        }

        JFrame frame = new JFrame("Skyclient Loader");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(new URL("https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/config/icon.png")));
        frame.setResizable(false);
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c2 = new GridBagConstraints();
        frame.setLayout(gb);
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.insets = new Insets(1, 1, 1, 1);

        JLabel lbar = new JLabel("Loading Checkboxes and images for Mods");
        lbar.setPreferredSize(new Dimension(500, 40));
        c2.gridx = 0;
        c2.gridy = 0;

        gb.setConstraints(lbar, c2);
        frame.add(lbar);


        JProgressBar bar = new JProgressBar();
        bar.setPreferredSize(new Dimension(500, 40));
        bar.setStringPainted(true);
        bar.setMaximum(6);
        c2.gridy = 1;

        gb.setConstraints(bar, c2);
        frame.add(bar);

        frame.pack();
        frame.setVisible(true);

        JPanel pane = new JPanel();
        JPanel pane2 = new JPanel();

        List<JCheckBox> Checkboxes = new ArrayList<>();
        List<JCheckBox> Checkboxes2 = new ArrayList<>();

        List<JButton> buttons = new ArrayList<>();
        List<JButton> buttons2 = new ArrayList<>();

        JButton button;

        List<JLabel> Labels = new ArrayList<>();
        List<JLabel> Labels2 = new ArrayList<>();

        JLabel Label;

        GridBagLayout gridbag = new GridBagLayout();

        GridBagConstraints c = new GridBagConstraints();

        truepane.setLayout(gridbag);
        pane.setLayout(gridbag);
        pane2.setLayout(gridbag);

        c.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < list.size(); i++) {
            try {

                String loc = "resources/images/icons/" + modsjson.getJSONObject(i).get("icon");

                sendLog("loc: " + loc,MainGui.class,LogType.INFO);

                BufferedImage myPicture;
                JarEntry entry = null;
                JarFile jar = null;

                InputStream is = MainGui.class.getResourceAsStream("/" + loc);

                sendLog("is: " + is,MainGui.class,LogType.INFO);

                try {
                    jar = new JarFile(new java.io.File(MainGui.class.getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .getPath())
                            .getName());
                    entry = jar.getJarEntry(loc);
                } catch (Exception ignored) {

                }

                sendLog("jar: " + jar,MainGui.class,LogType.INFO);
                sendLog("entry: " + entry,MainGui.class,LogType.INFO);

                URL url = new URL(("https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/icons/" + modsjson.getJSONObject(i).get("icon")).replaceAll(" ","%20"));
                InputStream inputStream = url.openStream();


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                org.apache.commons.io.IOUtils.copy(is, baos);
                byte[] isB = baos.toByteArray();

                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                org.apache.commons.io.IOUtils.copy(inputStream, baos2);
                byte[] inputStreamB = baos2.toByteArray();

                if (entry != null && is != null && Arrays.equals(inputStreamB,isB)) {
                    sendLog("same",MainGui.class,LogType.INFO);
                    myPicture = ImageIO.read(new ByteArrayInputStream(isB));
                } else {
                    myPicture = ImageIO.read(new ByteArrayInputStream(inputStreamB));
                }

                sendLog("mP: "+myPicture,MainGui.class,LogType.INFO);

                Labels.add(new JLabel(new ImageIcon(getScaledImage(myPicture, 50, 50))));

                Labels.get(Labels.size() - 1).setPreferredSize(new Dimension(50, 50));

                c.insets = new Insets(1, 1, 1, 1);
                c.gridx = 0;
                c.gridy = i;

                gridbag.setConstraints(Labels.get(Labels.size() - 1), c);

                pane.add(Labels.get(Labels.size() - 1));
            } catch (Exception e) {
                sendLog(Arrays.toString(e.getStackTrace()),MainGui.class,LogType.ERROR);
            }

            Checkboxes.add(new JCheckBox(list.get(i)));

            Checkboxes.get(i).setName((String) modsjson.getJSONObject(i).get("id"));

            try {
                if ((Boolean) modsjson.getJSONObject(i).get("enabled")) {

                    Checkboxes.get(i).setSelected(true);

                }
            } catch (Exception ignore) {

                Checkboxes.get(i).setSelected(false);

            }

            c.gridx = 1;
            c.gridy = i;

            gridbag.setConstraints(Checkboxes.get(i), c);

            pane.add(Checkboxes.get(i));

            buttons.add(new JButton("^"));
            buttons.get(i).setName((String) modsjson.getJSONObject(i).get("id"));

            c.gridx = 2;
            c.gridy = i;

            gridbag.setConstraints(buttons.get(i), c);

            pane.add(buttons.get(i));


        }

        lbar.setText("Loading Checkboxes and images for Packs");
        bar.setValue(1);

        for (int i = 0; i < packsjson.length(); i++) {
            try {
                String loc = "resources/images/icons/" + packsjson.getJSONObject(i).get("icon");

                sendLog("loc: " + loc,MainGui.class,LogType.INFO);

                JarEntry entry = null;
                JarFile jar = null;
                BufferedImage myPicture;

                InputStream is = MainGui.class.getResourceAsStream("/" + loc);

                sendLog("is: " + is,MainGui.class,LogType.INFO);

                try {
                    jar = new JarFile(new java.io.File(MainGui.class.getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .getPath())
                            .getName());
                    entry = jar.getJarEntry(loc);
                } catch (Exception ignored) {

                }

                sendLog("jar: " + jar,MainGui.class,LogType.INFO);
                sendLog("entry: " + entry,MainGui.class,LogType.INFO);

                URL url = new URL(("https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/icons/" + packsjson.getJSONObject(i).get("icon")).replaceAll(" ","%20"));
                InputStream inputStream = url.openStream();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                org.apache.commons.io.IOUtils.copy(is, baos);
                byte[] isB = baos.toByteArray();

                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                org.apache.commons.io.IOUtils.copy(inputStream, baos2);
                byte[] inputStreamB = baos2.toByteArray();

                if (entry != null && is != null && Arrays.equals(inputStreamB,isB)) {
                    sendLog("same",MainGui.class,LogType.INFO);
                    myPicture = ImageIO.read(new ByteArrayInputStream(isB));
                } else {
                    myPicture = ImageIO.read(new ByteArrayInputStream(inputStreamB));
                }

                sendLog("mP: "+myPicture,MainGui.class,LogType.INFO);

                Labels2.add(new JLabel(new ImageIcon(getScaledImage(myPicture, 50, 50))));
                Labels2.get(Labels2.size() - 1).setPreferredSize(new Dimension(50, 50));
                c.insets = new Insets(1, 1, 1, 1);
                c.gridx = 0;
                c.gridy = i;
                gridbag.setConstraints(Labels2.get(Labels2.size() - 1), c);
                pane2.add(Labels2.get(Labels2.size() - 1));
            } catch (Exception e) {
                sendLog(Arrays.toString(e.getStackTrace()),MainGui.class,LogType.ERROR);
            }

            Checkboxes2.add(new JCheckBox((String) packsjson.getJSONObject(i).get("display")));
            Checkboxes2.get(i).setName((String) packsjson.getJSONObject(i).get("id"));

            try {
                if ((Boolean) packsjson.getJSONObject(i).get("enabled")) {
                    Checkboxes2.get(i).setSelected(true);
                }
            } catch (Exception ignore) {
                Checkboxes2.get(i).setSelected(false);
            }

            c.gridx = 1;
            c.gridy = i;
            gridbag.setConstraints(Checkboxes2.get(i), c);
            pane2.add(Checkboxes2.get(i));

            buttons2.add(new JButton("^"));
            buttons2.get(i).setName((String) packsjson.getJSONObject(i).get("id"));
            c.gridx = 2;
            c.gridy = i;
            gridbag.setConstraints(buttons2.get(i), c);
            pane2.add(buttons2.get(i));

        }

        lbar.setText("Loading Popup Menus for Mods");
        bar.setValue(2);

        for (int i = 0; i < buttons.size(); i++) {
            JButton lab = buttons.get(i);
            JSONArray json = modsjson.getJSONObject(i).getJSONArray("actions");

            final JPopupMenu menu = Popup(json);

            lab.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }

                public void mouseReleased(MouseEvent e) {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            });
        }

        lbar.setText("Loading Popup Menus for Packs");
        bar.setValue(3);

        for (int i = 0; i < buttons2.size(); i++) {
            JButton lab = buttons2.get(i);
            try {
                JSONArray json = packsjson.getJSONObject(i).getJSONArray("actions");

                final JPopupMenu menu = Popup(json);

                lab.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        menu.show(e.getComponent(), e.getX(), e.getY());
                    }

                    public void mouseReleased(MouseEvent e) {
                        menu.show(e.getComponent(), e.getX(), e.getY());
                    }
                });
            } catch (Exception ignored) {
                lab.setOpaque(false);
                lab.setContentAreaFilled(false);
                lab.setBorderPainted(false);
                lab.setText("");
                lab.setEnabled(false);
            }
        }

        lbar.setText("Loading Warnings for Mods");
        bar.setValue(4);

        for (int i = 0; i < Checkboxes.size(); i++) {
            JCheckBox lab = Checkboxes.get(i);
            try {
                JSONArray json = modsjson.getJSONObject(i).getJSONObject("warning").getJSONArray("lines");


                lab.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        Boolean selected = false;

                        lab.setSelected(false);
                        try {
                            selected = Warning(json);
                        } catch (MalformedURLException ex) {
                            sendLog(Arrays.toString(ex.getStackTrace()),MainGui.class,LogType.ERROR);
                        }

                        lab.setSelected(selected);

                    }

                });
            } catch (Exception ignored) {

            }

        }

        lbar.setText("Loading Buttons and Labels");
        bar.setValue(5);

        button = new JButton("Install");
        button.setPreferredSize(new Dimension(200, 30));
        c.insets = new Insets(1, 1, 1, 3);
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 2;
        gridbag.setConstraints(button, c);
        truepane.add(button);

        JButton button2 = new JButton("Select Path");
        button2.setPreferredSize(new Dimension(50, 30));
        c.insets = new Insets(1, 1, 1, 3);
        c.gridwidth = 1;
        c.gridx = 3;
        c.gridy = 3;
        gridbag.setConstraints(button2, c);
        truepane.add(button2);

        JLabel LPath = new JLabel(mc);
        LPath.setPreferredSize(new Dimension(150, 30));
        c.insets = new Insets(1, 1, 1, 3);
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 3;
        gridbag.setConstraints(LPath, c);
        truepane.add(LPath);

        Label = new JLabel("Mods", SwingConstants.CENTER);
        Label.setPreferredSize(new Dimension(200, 30));
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        gridbag.setConstraints(Label, c);
        truepane.add(Label);

        JScrollPane sp = new JScrollPane(pane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.setPreferredSize(new Dimension(370, 500));
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        gridbag.setConstraints(sp, c);
        truepane.add(sp);

        //line 2

        Label = new JLabel("Packs", SwingConstants.CENTER);
        Label.setPreferredSize(new Dimension(200, 30));
        c.gridwidth = 2;
        c.gridx = 2;
        c.gridy = 0;
        gridbag.setConstraints(Label, c);
        truepane.add(Label);

        JScrollPane sp2 = new JScrollPane(pane2, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp2.getVerticalScrollBar().setUnitIncrement(16);
        sp2.setPreferredSize(new Dimension(370, 500));
        c.gridwidth = 2;
        c.gridx = 2;
        c.gridy = 1;
        gridbag.setConstraints(sp2, c);
        truepane.add(sp2);

        lbar.setText("Loading Click Detectors");
        bar.setValue(5);

        button2.addActionListener(ae -> {

            JFrame frame2 = new JFrame("Select Path");
            frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            try {
                frame2.setIconImage(Toolkit.getDefaultToolkit().getImage(new URL("https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/config/icon.png")));
            } catch (MalformedURLException ignored) {
            }
            frame2.setResizable(false);

            JFileChooser path = new JFileChooser();
            path.setCurrentDirectory(new File(LPath.getText()));
            path.setDialogTitle("select folder");
            path.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            path.setAcceptAllFileFilterUsed(false);

            frame2.add(path);

            frame2.pack();
            frame2.setVisible(true);

            path.addActionListener(e -> {
                if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {

                    LPath.setText(path.getSelectedFile().getAbsolutePath());
                    frame2.dispose();
                } else if (e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
                    frame2.dispose();
                }
            });


        });


        button.addActionListener(ae -> {
            String finalMc = LPath.getText();
            List<String> packs = new ArrayList<>();
            List<String> mods = new ArrayList<>();
            MainCode main = new MainCode();

            for (JCheckBox checkbox : Checkboxes) {
                if (checkbox.isSelected()) {
                    mods.add(checkbox.getName());
                }
            }

            for (JCheckBox checkbox : Checkboxes2) {
                if (checkbox.isSelected()) {
                    packs.add(checkbox.getName());
                }
            }

            button.setEnabled(false);

            try {
                main.code(mods, packs, finalMc);
            } catch (IOException | URISyntaxException e) {
                sendLog(Arrays.toString(e.getStackTrace()),MainGui.class,LogType.ERROR);
            }

        });

        lbar.setText("Loaded everything!");
        bar.setValue(6);

        frame.dispose();

    }

    public static void Guide(String text) {


        Runnable guideThread = () -> {
            JFrame frame = new JFrame("Guide");
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            try {
                frame.setIconImage(Toolkit.getDefaultToolkit().getImage(new URL("https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/config/icon.png")));
            } catch (MalformedURLException ignored) {

            }
            frame.setResizable(false);

            JLabel label;
            JPanel pane = new JPanel();
            GridBagLayout gridbag = new GridBagLayout();
            GridBagConstraints c = new GridBagConstraints();
            frame.setLayout(gridbag);
            pane.setLayout(gridbag);
            c.fill = GridBagConstraints.HORIZONTAL;

            java.util.List<JLabel> labels = mdToList(text);

            //System.out.println(textParsed);

            for (int i = 0; i < (labels.size()); i++) {
                label = labels.get(i);
                label.setVerticalAlignment(JLabel.TOP);
                c.gridx = 0;
                c.gridy = i;
                gridbag.setConstraints(label, c);
                pane.add(label);
                sendLog(label+"",MainGui.class,LogType.INFO);
            }

            JScrollPane sp = new JScrollPane(pane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            sp.getVerticalScrollBar().setUnitIncrement(16);
            sp.setPreferredSize(new Dimension(800, 600));
            c.gridwidth = 0;
            c.gridx = 0;
            c.gridy = 0;
            gridbag.setConstraints(sp, c);
            frame.add(sp);

            frame.pack();
            frame.setVisible(true);
        };

        new Thread(guideThread).start();

    }

    public static JPopupMenu Popup(final JSONArray array) {

        JPopupMenu popupMenu = new JPopupMenu();
        List<JMenuItem> items = new ArrayList<>();


        for (int i = 0; i < (array.length()); i++) {

            try {
                final String md = (String) array.getJSONObject(i).get("document");

                items.add(new JMenuItem("Guide (Markdown Reader)"));
                popupMenu.add(items.get(i));

                items.get(i).addMouseListener(new MouseAdapter() {

                    public void mouseReleased(MouseEvent e) {
                        try {
                            Guide(request(md));
                        } catch (Exception ex) {

                            sendLog(Arrays.toString(ex.getStackTrace()),MainGui.class,LogType.ERROR);
                        }
                    }
                });

            } catch (Exception e) {
                final String text = (String) array.getJSONObject(i).get("text");
                final String link = (String) array.getJSONObject(i).get("link");

                items.add(new JMenuItem(text));
                popupMenu.add(items.get(i));

                items.get(i).addMouseListener(new MouseAdapter() {

                    public void mouseReleased(MouseEvent e) {
                        try {
                            URI uri = new URI(link);

                            java.awt.Desktop.getDesktop().browse(uri);
                        } catch (Exception ex) {
                            sendLog(Arrays.toString(ex.getStackTrace()),MainGui.class,LogType.ERROR);
                        }
                    }
                });

            }

        }
        popupMenu.setSize(popupMenu.getWidth() * 4, popupMenu.getHeight() * 4);

        return popupMenu;
    }

    public static Boolean Warning(final JSONArray array) throws MalformedURLException {

        String arrrayJoined = "<html><div style='text-align: center;'>" + array.join("<br>") + "</div></html>";
        boolean val = false;

        int option = JOptionPane.showConfirmDialog(null, arrrayJoined.replaceAll("\"", ""), "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            val = true;
        }

        return val;
    }

    public static void UpdateCheck() throws IOException, URISyntaxException {

        JarFile jar = new JarFile(new java.io.File(MainGui.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath())
                .getName());

        Manifest manifest = jar.getManifest();
        Attributes attr = manifest.getMainAttributes();

        int ver = Integer.parseInt(attr.getValue("Version").replaceAll("\\.",""));

        JSONObject gitVersionJson = new JSONObject(request("https://raw.githubusercontent.com/koxx12-dev/Skyclient-installer-Java/main/version.json"));
        int gitVersion = Integer.parseInt(gitVersionJson.get("version").toString().replaceAll("\\.",""));

        if (ver < gitVersion) {
            JOptionPane.showMessageDialog(null, "Newer version of the installer was detected\nYour version is \""+attr.getValue("Version")+"\" newest one is \""+gitVersionJson.get("version")+"\"", "New version", JOptionPane.ERROR_MESSAGE);
            java.awt.Desktop.getDesktop().browse(new URI("https://github.com/koxx12-dev/Skyclient-installer-Java/releases/latest"));
            System.exit(0);
        }

    }
}
