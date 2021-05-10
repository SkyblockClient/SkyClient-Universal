package io.github.koxx12_dev.skyclient_installer_java;

import org.apache.commons.lang.SystemUtils;
import org.json.JSONArray;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainGui extends Utils {

    public static void main(String[] args) throws IOException {

        PrintStream fileOut = new PrintStream("./log.txt");
        System.setOut(fileOut);

        List<String> displayed = new ArrayList<>();

        String modsrq = request("https://raw.githubusercontent.com/nacrt/SkyblockClient-REPO/main/files/mods.json");
        String packsrq = request("https://raw.githubusercontent.com/nacrt/SkyblockClient-REPO/main/files/packs.json");

        JSONArray packsjson = new JSONArray(packsrq);
        JSONArray modsjson = new JSONArray(modsrq);

        modsjson.remove(modsjson.length()-1);
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

            } catch (Exception e){
                displayed.add((String) modsjson.getJSONObject(i).get("display"));
            }

            
        }

        System.out.println(displayed);


        GuiInit(displayed,modsjson,packsjson);
    }

    public static void GuiInit(List list,JSONArray modsjson,JSONArray packsjson) throws IOException {
        JFrame frame = new JFrame("Skyclient Installer");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(new URL("https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/config/icon.png")));
        frame.setResizable(false);
        Gui(frame.getContentPane(),list,modsjson,packsjson);
        frame.pack();
        frame.setVisible(true);
    }

    public static void Gui(Container truepane,List list,JSONArray modsjson,JSONArray packsjson) {

        JPanel pane = new JPanel();
        JPanel pane2 = new JPanel();
        List<JCheckBox> Checkboxes = new ArrayList<>();
        List<JCheckBox> Checkboxes2 = new ArrayList<>();
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
                String url = "https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/icons/"+modsjson.getJSONObject(i).get("icon");
                BufferedImage myPicture = ImageIO.read(new URL(url.replaceAll(" ","%20")));
                Labels.add(new JLabel(new ImageIcon(getScaledImage(myPicture,50,50))));
                Labels.get(Labels.size()-1).setPreferredSize(new Dimension(50, 50));
                c.insets = new Insets(1,1,1,1);
                c.gridx = 0;
                c.gridy = i;
                gridbag.setConstraints(Labels.get(Labels.size()-1), c);
                pane.add(Labels.get(Labels.size()-1));
            } catch (Exception ignored){

            }

            Checkboxes.add(new JCheckBox((String) list.get(i)));
            Checkboxes.get(i).setName((String) modsjson.getJSONObject(i).get("id"));

            try {
                if ((Boolean) modsjson.getJSONObject(i).get("enabled")) {
                    Checkboxes.get(i).setSelected(true);
                }
            } catch (Exception ignore){
                Checkboxes.get(i).setSelected(false);
            }

            c.gridx = 1;
            c.gridy = i;
            gridbag.setConstraints(Checkboxes.get(i), c);
            pane.add(Checkboxes.get(i));

        }

        for (int i = 0; i < packsjson.length(); i++) {
            try {
                String url = "https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/icons/"+packsjson.getJSONObject(i).get("icon");
                BufferedImage myPicture = ImageIO.read(new URL(url.replaceAll(" ","%20")));
                Labels2.add(new JLabel(new ImageIcon(getScaledImage(myPicture,50,50))));
                Labels2.get(Labels2.size()-1).setPreferredSize(new Dimension(50, 50));
                c.insets = new Insets(1,1,1,1);
                c.gridx = 0;
                c.gridy = i;
                gridbag.setConstraints(Labels2.get(Labels2.size()-1), c);
                pane2.add(Labels2.get(Labels2.size()-1));
            } catch (Exception ignored){

            }

            Checkboxes2.add(new JCheckBox((String) packsjson.getJSONObject(i).get("display")));
            Checkboxes2.get(i).setName((String) packsjson.getJSONObject(i).get("id"));

            try {
                if ((Boolean) packsjson.getJSONObject(i).get("enabled")) {
                    Checkboxes2.get(i).setSelected(true);
                }
            } catch (Exception ignore){
                Checkboxes2.get(i).setSelected(false);
            }

            c.gridx = 1;
            c.gridy = i;
            gridbag.setConstraints(Checkboxes2.get(i), c);
            pane2.add(Checkboxes2.get(i));

        }

        for (int i = 0; i < Labels.size(); i++) {
            JLabel lab = Labels.get(i);
            JSONArray json = modsjson.getJSONObject(i).getJSONArray("actions");

            final JPopupMenu menu = Popup(json);

            lab.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (e.isPopupTrigger())
                        menu.show(e.getComponent(), e.getX(), e.getY());
                }

                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger())
                        menu.show(e.getComponent(), e.getX(), e.getY());
                }
                });
        }

        for (int i = 0; i < Labels2.size(); i++) {
            JLabel lab = Labels2.get(i);
            try {
                JSONArray json = packsjson.getJSONObject(i).getJSONArray("actions");

                final JPopupMenu menu = Popup(json);

                lab.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (e.isPopupTrigger())
                            menu.show(e.getComponent(), e.getX(), e.getY());
                    }

                    public void mouseReleased(MouseEvent e) {
                        if (e.isPopupTrigger())
                            menu.show(e.getComponent(), e.getX(), e.getY());
                    }
                });
            } catch (Exception ignored) {

            }
        }

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
                        } catch (MalformedURLException malformedURLException) {
                            malformedURLException.printStackTrace();
                        }

                        lab.setSelected(selected);

                    }
//
                    //public void mouseReleased(MouseEvent e) {
                    //    if (e.isPopupTrigger())
                    //        menu.show(e.getComponent(), e.getX(), e.getY());
                    //}
                });
            } catch (Exception ignored) {

            }

        }

        button = new JButton("Install");
        button.setPreferredSize(new Dimension(200,30));
        c.insets = new Insets(1,1,1,3);
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 2;
        gridbag.setConstraints(button, c);
        truepane.add(button);

        Label = new JLabel("Mods", SwingConstants.CENTER);
        Label.setPreferredSize(new Dimension(200,30));
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        gridbag.setConstraints(Label, c);
        truepane.add(Label);
        
        JScrollPane sp = new JScrollPane(pane,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setPreferredSize(new Dimension(270, 400));
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        gridbag.setConstraints(sp, c);
        truepane.add(sp);

        //line 2

        Label = new JLabel("Packs", SwingConstants.CENTER);
        Label.setPreferredSize(new Dimension(200,30));
        c.gridwidth = 2;
        c.gridx = 2;
        c.gridy = 0;
        gridbag.setConstraints(Label, c);
        truepane.add(Label);

        JScrollPane sp2 = new JScrollPane(pane2,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp2.setPreferredSize(new Dimension(270, 400));
        c.gridwidth = 2;
        c.gridx = 2;
        c.gridy = 1;
        gridbag.setConstraints(sp2, c);
        truepane.add(sp2);

        button.addActionListener(ae -> {

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
                main.code(mods,packs);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }

        });


    }

    public static void Guide(String text) throws IOException {



        JFrame frame = new JFrame("Guide");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(new URL("https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/config/icon.png")));
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

        for (int i = 0; i < (labels.size()); i++)  {
            label = labels.get(i);
            label.setVerticalAlignment(JLabel.TOP);
            c.gridx = 0;
            c.gridy = i;
            gridbag.setConstraints(label, c);
            pane.add(label);
            System.out.println(label);
        }

        JScrollPane sp = new JScrollPane(pane,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setPreferredSize(new Dimension(800, 600));
        c.gridwidth = 0;
        c.gridx = 0;
        c.gridy = 0;
        gridbag.setConstraints(sp, c);
        frame.add(sp);

        frame.pack();
        frame.setVisible(true);

    }

    public static JPopupMenu Popup(final JSONArray array) {

        JPopupMenu popupMenu = new JPopupMenu();
        List<JMenuItem> items = new ArrayList<>();

        for (int i = 0; i < (array.length()); i++) {

            try {
                final String md = (String)array.getJSONObject(i).get("document");

                items.add(new JMenuItem("Guide (Markdown Reader)"));
                popupMenu.add(items.get(i));

                items.get(i).addMouseListener(new MouseAdapter() {

                    public void mouseReleased(MouseEvent e) {
                            try {
                                Guide(request(md));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                    }
                });

            } catch (Exception e) {
                final String text = (String)array.getJSONObject(i).get("text");
                final String link = (String)array.getJSONObject(i).get("link");

                items.add(new JMenuItem(text));
                popupMenu.add(items.get(i));

                items.get(i).addMouseListener(new MouseAdapter() {

                    public void mouseReleased(MouseEvent e) {
                        try {
                            URI uri = new URI(link);

                            java.awt.Desktop.getDesktop().browse(uri);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });

            }

        }
        return popupMenu;
    }

    public static Boolean Warning(final JSONArray array) throws MalformedURLException {

        String arrrayJoined = "<html><div style='text-align: center;'>" + array.join("<br>") + "</div></html>";
        boolean val = false;

        int option = JOptionPane.showConfirmDialog(null, arrrayJoined.replaceAll("\"", ""), "Warning", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

        if (option == JOptionPane.YES_OPTION){
            val = true;
        }

        return val;
    }
}
