package io.github.koxx12_dev.skyclient_installer_macos;

import org.apache.commons.lang.SystemUtils;
import org.json.JSONArray;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainGui extends Utils {

    public static void main(String[] args) throws IOException {

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
                Label = new JLabel(new ImageIcon(getScaledImage(myPicture,50,50)));
                Label.setPreferredSize(new Dimension(50, 50));
                c.insets = new Insets(1,1,1,1);
                c.gridx = 0;
                c.gridy = i;
                gridbag.setConstraints(Label, c);
                pane.add(Label);
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
                Label = new JLabel(new ImageIcon(getScaledImage(myPicture,50,50)));
                Label.setPreferredSize(new Dimension(50, 50));
                c.insets = new Insets(1,1,1,1);
                c.gridx = 0;
                c.gridy = i;
                gridbag.setConstraints(Label, c);
                pane2.add(Label);
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

}
