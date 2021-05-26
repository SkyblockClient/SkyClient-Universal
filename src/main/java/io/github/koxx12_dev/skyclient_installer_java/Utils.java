package io.github.koxx12_dev.skyclient_installer_java;

import com.github.rjeschke.txtmark.Processor;
import org.apache.commons.lang.SystemUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.zip.GZIPOutputStream;

public class Utils {

    public static String request(String URL) throws IOException {

        sendLog("Requested: "+URL,Utils.class,LogType.INFO);

        java.net.URL url = new URL(URL);
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String data = s.hasNext() ? s.next() : "";
        sendLog("Returned: "+data,Utils.class,LogType.INFO);
        return data;

    }

    public static void Download(String URL, String Loc) {
        try {

            sendLog("Trying to download: "+URL,Utils.class,LogType.INFO);

            java.net.URL url = new URL(URL);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "NING/1.0");
            BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            FileOutputStream fileOS = new FileOutputStream(Loc);
            byte[] data = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
            }

            sendLog("Downloaded: " + URL,MainCode.class,LogType.INFO);

        } catch (Exception e) {

            StringWriter error = new StringWriter();
            e.printStackTrace(new PrintWriter(error));
            sendLog("Failed to download: "+URL+"\nReason: "+error, Utils.class, LogType.ERROR);

        }

    }

    public static Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    public static String replaceWithOsPath(String path) {
        String plc = "";
        if (SystemUtils.IS_OS_WINDOWS) {
            plc = "\\\\";
        } else if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_LINUX) {
            plc = "/";
        } else {

            sendLog("HOW TF DID YOU GOT HERE WITH " + System.getProperty("os.name") + "\nIT SHOULDN'T BE POSSIBLE", Utils.class, LogType.FATAL);
            System.exit(-1);
        }

        return path.replaceAll("\\|\\|", plc);
    }

    public static String urlEncode(String toEncode) {
        //if null, keep null (no gain or loss of safety)
        if (toEncode == null)
            return null;

        StringBuilder sb = new StringBuilder();
        for (char character : toEncode.toCharArray())//for every character in the string
            switch (character) {//if the character needs to be escaped, add its escaped value to the StringBuilder
                case '!':
                    sb.append("%21");
                    continue;
                case '#':
                    sb.append("%23");
                    continue;
                case '$':
                    sb.append("%24");
                    continue;
                case '&':
                    sb.append("%26");
                    continue;
                case '\'':
                    sb.append("%27");
                    continue;
                case '(':
                    sb.append("%28");
                    continue;
                case ')':
                    sb.append("%29");
                    continue;
                case '*':
                    sb.append("%2A");
                    continue;
                case '+':
                    sb.append("%2B");
                    continue;
                case ',':
                    sb.append("%2C");
                    continue;
                case '/':
                    sb.append("%2F");
                    continue;
                case ':':
                    sb.append("%3A");
                    continue;
                case ';':
                    sb.append("%3B");
                    continue;
                case '=':
                    sb.append("%3D");
                    continue;
                case '?':
                    sb.append("%3F");
                    continue;
                case '@':
                    sb.append("%40");
                    continue;
                case '[':
                    sb.append("%5B");
                    continue;
                case ']':
                    sb.append("%5D");
                    continue;
                case ' ':
                    sb.append("%20");
                    continue;
                case '"':
                    sb.append("%22");
                    continue;
                case '%':
                    sb.append("%25");
                    continue;
                case '-':
                    sb.append("%2D");
                    continue;
                case '<':
                    sb.append("%3C");
                    continue;
                case '>':
                    sb.append("%3E");
                    continue;
                case '\\':
                    sb.append("%5C");
                    continue;
                case '^':
                    sb.append("%5E");
                    continue;
                case '_':
                    sb.append("%5F");
                    continue;
                case '`':
                    sb.append("%60");
                    continue;
                case '{':
                    sb.append("%7B");
                    continue;
                case '|':
                    sb.append("%7C");
                    continue;
                case '}':
                    sb.append("%7D");
                    continue;
                case '~':
                    sb.append("%7E");
                    continue;
                default:
                    sb.append(character);//if it does not need to be escaped, add the character itself to the StringBuilder
            }
        return sb.toString();//build the string, and return
    }

    public static java.util.List<JLabel> mdToList(String mdString) {
        // separate input by spaces ( URLs don't have spaces )
        java.util.List<JLabel> labels = new ArrayList<>();

        String textParsed = Processor.process(mdString);

        java.util.List<String> lines = Arrays.asList(textParsed.split("\n"));

        sendLog(lines + "", Utils.class, LogType.INFO);

        for (String line : lines) {

            if (line.contains("img")) {
                //System.out.println(line+" , img");
                String[] img = line.split("\"");
                for (String imgsplitted : img) {
                    try {
                        URL url = new URL(imgsplitted);

                        final HttpURLConnection connection = (HttpURLConnection) url
                                .openConnection();
                        connection.setRequestProperty(
                                "User-Agent",
                                "NING/1.0");

                        BufferedImage myPicture = ImageIO.read(connection.getInputStream());
                        labels.add(new JLabel(new ImageIcon(getScaledImage(myPicture, myPicture.getWidth() / 2, myPicture.getHeight() / 2))));


                    } catch (Exception ignored) {

                    }
                }
            } else {

                labels.add(new JLabel("<html>" + line + "</html>"));
            }


        }

        return labels;

    }

    public static void sendLog(String Message, Class<?> _class, LogType type) {

        System.out.println("[" + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()) + "] [" + _class.getSimpleName() + " / " + type + "]: " + Message);

    }

    public static void createLogFile() throws IOException {

        String _sc = null;

        if (SystemUtils.IS_OS_WINDOWS) {
            _sc = System.getenv("APPDATA") + "\\.skyclient";
        } else if (SystemUtils.IS_OS_MAC) {
            _sc = System.getenv("HOME") + "/Library/Application Support/skyclient";
        } else if (SystemUtils.IS_OS_LINUX) {
            _sc = System.getenv("HOME") + "/.skyclient";
        }

        if (_sc != null) {

            if (!new File(_sc).exists()) {

                sendLog("created .skyclient", Utils.class, LogType.INFO);

                new File(_sc).mkdir();

            }

            if (!new File(_sc + "/logs").exists()) {

                sendLog("created .skyclient/logs", Utils.class, LogType.INFO);

                new File(_sc + "/logs").mkdir();

            }

            if (new File(_sc + "/logs/latest.log").exists()) {

                sendLog("gunziped log", Utils.class, LogType.INFO);

                LocalDateTime logCreatonTime = Files.readAttributes(Paths.get(_sc + "/logs/latest.log"), BasicFileAttributes.class).lastModifiedTime()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

                gzipFile(_sc + "/logs/latest.log", _sc + "/logs/" + logCreatonTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy_HH-mm-ss")) + ".log.gz");

                new File(_sc + "/logs/latest.log").delete();

            }

            PrintStream out = new PrintStream(new FileOutputStream(_sc + "/logs/latest.log"));
            System.setOut(out);

        } else {

            sendLog("Failed to create Log File", Utils.class, LogType.FATAL);
            System.exit(-1);

        }

    }

    public static void gzipFile(String filepath, String destinaton) {

        byte[] buffer = new byte[1024];

        try {

            FileOutputStream fileOutputStream = new FileOutputStream(destinaton);

            GZIPOutputStream gzipOuputStream = new GZIPOutputStream(fileOutputStream);

            FileInputStream fileInput = new FileInputStream(filepath);

            int bytes_read;

            while ((bytes_read = fileInput.read(buffer)) > 0) {
                gzipOuputStream.write(buffer, 0, bytes_read);
            }

            fileInput.close();

            gzipOuputStream.finish();
            gzipOuputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}