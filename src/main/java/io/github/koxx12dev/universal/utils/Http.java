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

public class Http {

    //create a simple get request
    public static String get(String url) {
        try {
            java.net.URL obj = new java.net.URL(url);
            java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void download(String url, String path) {
        //download file with a User Agent

        try {
            System.out.println("Downloading " + url + " to " + path);
            java.net.URL obj = new java.net.URL(url);
            java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setConnectTimeout(5000);
            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                java.io.BufferedInputStream in = new java.io.BufferedInputStream(con.getInputStream());
                java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                in.close();
            } else {
                System.out.println(responseCode + " | " + url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String urlEscape(String toEncode) {
        //escape url
        //noinspection deprecation
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

}
