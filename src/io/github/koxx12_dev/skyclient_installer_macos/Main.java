package io.github.koxx12_dev.skyclient_installer_macos;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {

        String os = System.getProperty("os.name");
        List<Boolean> slcted = new ArrayList<>();
        List<Boolean> txtp = new ArrayList<>();
        String folderloc = "str";

        if (!os.equals("Mac OS X")) {
            javax.swing.JOptionPane.showMessageDialog( null, "Its called \"Mac OS Installer\" \nfor a reson u dumbass" );
            System.exit(0);
        } else {
            folderloc = System.getenv("HOME") + "/Library/Application Support/minecraft";
            clearScreen();
        }

        if (System.console() == null) {
            javax.swing.JOptionPane.showMessageDialog( null, "Use the terminal u dumbass\nnot this jar" );
            System.exit(0);
        }

        String cd = new File(Main.class.getProtectionDomain().getCodeSource().getLocation()
                .toURI()).getParent();

        File sctmp = new File(cd+"/sc_tmp/");

        if (sctmp.exists()) {
            FileUtils.deleteDirectory(sctmp);
        }
        sctmp.mkdir();

        downloadrepo("https://github.com/nacrt/SkyblockClient-REPO.git",sctmp.getPath()+"/files_tmp/");

        File tmpfiles = new File(cd+"/sc_tmp/files_tmp/");
        File files = new File(cd+"/sc_tmp/files_tmp/files");

        FileUtils.moveDirectory(files,new File(cd+"/sc_tmp/files"));

        files = new File(cd+"/sc_tmp/files");

        FileUtils.deleteDirectory(tmpfiles);

        JSONArray arry = new JSONArray(FileUtils.readFileToString(new File(cd + "/sc_tmp/files/mods.json"),"UTF-8"));

        //System.out.println(arry);


        for (int i = 0; i < (arry.length()-1); i++) {
            JSONObject obj = arry.getJSONObject(i);
            String dpname = obj.get("display").toString();
            String filenm = obj.get("file").toString();

            System.out.println("");
            System.out.println("Do you want to install "+dpname+" ? ("+filenm+")");

            try {
                JSONArray wrns = obj.getJSONObject("warning").getJSONArray("lines");

                System.out.println(TerminalColors.WARNING.getValue() + "WARNING");
                for (int x = 0; x < wrns.length(); x++){
                    System.out.println(wrns.get(x));
                }
                System.out.print(TerminalColors.ENDC.getValue());
            }catch(Exception ignored){

            }

            Scanner op = new Scanner(System.in);

            System.out.print("[y/n]:");

            String answ = op.nextLine();

            if (answ.equals("no") || answ.equals("n")) {
                slcted.add(false);
            } else {
                slcted.add(true);
            }

            clearScreen();

        }

        List<String> slctednms = new ArrayList<>();

        for (int i = 0; i < (slcted.size()); i++) {
            if (slcted.get(i)) {

                JSONObject obj = arry.getJSONObject(i);
                String nme = obj.get("file").toString();

                slctednms.add(nme);
            }
        }

        System.out.println("Is this Correct?");
        System.out.println("");
        System.out.println(String.join(" , ",slctednms));
        System.out.println("");

        Scanner op = new Scanner(System.in);

        System.out.print("[y/n]:");

        String crrct = op.nextLine();

        if (crrct.equals("no") || crrct.equals("n")) {
            FileUtils.deleteDirectory(sctmp);
            System.exit(0);
        }

        File scfldr = new File(folderloc + "/skyclient");
        File scfldrmds = new File(folderloc + "/skyclient/mods");
        File scfldrcfg = new File(folderloc + "/skyclient/config");
        File scfldrtxt = new File(folderloc + "/skyclient/resourcepacks");

        if (scfldr.exists()) {
            FileUtils.deleteDirectory(scfldr);
        }
        scfldr.mkdir();
        scfldrmds.mkdir();
        scfldrcfg.mkdir();
        scfldrtxt.mkdir();

        clearScreen();

        for (int i = 0; i < (slcted.size()); i++) {
            if (slcted.get(i)) {

                JSONObject obj = arry.getJSONObject(i);
                String dpname = obj.get("display").toString();
                String filenm = obj.get("file").toString();
                String id = obj.get("id").toString();

                String url;

                if(id.equals("rpm")){
                    url = "https://github.com/nacrt/SkyblockClient-REPO/blob/main/files/mods/" + filenm.replaceAll(" ", "%20") + "?raw=true";
                }else{
                    url = obj.get("url").toString();
                }


                System.out.println("Started Downloading "+dpname+" ("+filenm+")");

                Download(url,folderloc + "/skyclient/mods/" + filenm);

                if(id.equals("itlt")){

                    File ilitfldr = new File(folderloc + "/skyclient/config/itlt");

                    ilitfldr.mkdir();

                    FileUtils.moveFile(new File(cd + "/sc_tmp/files/config/icon.png"), new File(folderloc + "/skyclient/config/itlt/icon.png"));
                    FileUtils.moveFile(new File(cd + "/sc_tmp/files/config/itlt.cfg"), new File(folderloc + "/skyclient/config/itlt.cfg"));

                } else if(id.equals("cmm")){

                    File cmmfldr = new File(folderloc + "/skyclient/config/CustomMainMenu");

                    FileUtils.moveFile(new File(cd + "/sc_tmp/files/config/play.json"), new File(folderloc + "/skyclient/config/CustomMainMenu/play.json"));
                    FileUtils.moveFile(new File(cd + "/sc_tmp/files/config/mainmenu.json"),new File(folderloc + "/skyclient/config/CustomMainMenu/mainmenu.json"));

                }

                System.out.println("Downloaded "+ dpname +" ("+filenm+")");
                System.out.println("");

            }
        }

        File frgfldr = new File(folderloc + "/versions/1.8.9-forge1.8.9-11.15.1.2318-1.8.9/");

        if (!frgfldr.exists()) {
            frgfldr.mkdir();
            FileUtils.moveFile(new File(cd + "/sc_tmp/files/forge/forge-1.8.9-11.15.1.2318-1.8.9.jar"), new File(folderloc + "/versions/1.8.9-forge1.8.9-11.15.1.2318-1.8.9/forge-1.8.9-11.15.1.2318-1.8.9.jar"));
            FileUtils.moveFile(new File(cd + "/sc_tmp/files/forge/1.8.9-forge1.8.9-11.15.1.2318-1.8.9.json"), new File(folderloc + "/versions/1.8.9-forge1.8.9-11.15.1.2318-1.8.9/1.8.9-forge1.8.9-11.15.1.2318-1.8.9.json"));
        }

        File profiles = new File(folderloc + "/launcher_profiles.json");

        JSONObject profilesjson = new JSONObject(FileUtils.readFileToString(profiles,"UTF-8"));

        JSONObject sc_pf = new JSONObject();
        sc_pf.put("created", JSONObject.NULL);
        sc_pf.put("gameDir", folderloc + "/skyclient");
        sc_pf.put("icon", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAMAAAD04JH5AAAACGFjVEwAAAAEAAAAAHzNZtAAAAAzUExURf//ACOQY5HbaR68c////11dXXR0dIWFhc3fbNDq6T09PWG/5j+Y2KjQ2Sdyxtrq+k2b5npKrGcAAAABdFJOUwBA5thmAAAAGmZjVEwAAAAAAAAAgAAAAIAAAAAAAAAAAAAIAGQAAK+px9AAAAKZSURBVHja7ZvpbgIxDISBQrcFerz/0zYjZarp4F0OocYgz5898PEhWXESwmp1Rpuu1SgVQAqAl6Z7QNwUowCGAjA5tO6as/1o2nbN2cD/KogCSAHw1kSAyBkJ37u2IrdjISNeATwGAI0pFqFCeFJ9xr0mp28BPAaAFgyTR4ORJ8RgxIL0ePBjvALID8CEdGBTigqRTQjXpYZGv4uaUQEMBWASB4Cu+hZN9Hf4Rf8CGArAAUgHjY2In9Pm0ma2NhVAXgBNxORaOFpMDqBBdSLDGK4CyAegAw+LzZuIBtOEXMDqQiYqQC3yE4gCGA5AZx94IgB/1k0MHbAiAIUogDwAPvlgIUUTCW9IGlQ3tebswkIsgKEAvhDVhYgaewKfYCxBRfEKIDdAFMATfDYduw5NHlhjaAG6TQGMB9Aiigowak6HrqMIQOcAfGJaADkA9Mckbxo6yXBnbEyxALExOTW9NvkEdG5h8pu8AFIAvHbtm2iw69JnJCEUN6gBgPupC3G0APmecXgPuwLIB4AkgNBgfO8AKED+YIFBaBLBZjIhDu8RqwByAfAFr2qsACww3HMQggBAnyiGxqHNn5VxAQwHIAQKUAtPA3hgAqAIcaWv20UxCyA3AAPwfglAC3HfpXb6pXaicKe8AIYDQAxARzpPgTQ5RH9PHsUsgLwACqEAPpnAFQ2IC1QCeGNjMj/oUgC5AQhBZwb0SaoCQJE/Dzb5gTc/6FQAuQD8gJo2kQiAEEsHnVVnD0AXwFCAr6bogOrOhALTRhQBePF5zALIB6AfRgOIN6nvprmByAuRg4/GPWlKBTAcwJPqM5uHTjKRlBtUOiG5WQUwHEAhzk0cdZOqAJ4L4BqxOAGw9Ae3AnhOgGiy8a8QBXBPgB9G8drRaA0Z2QAAABpmY1RMAAAAAQAAABQAAAA8AAAANAAAADwACABkAAETQ/wcAAAAWmZkQVQAAAACeNq1kzEOACAIAx1cjMT/P9eSSCLqJOU2joVAKQOUk6BUOmBLRUAFLLkX2mTIbNg3u7ZEkExssS4UQZmZ1WcQPmXGf9rAbvCgNCELlrRwNRCUEzPhDNF8OvhOAAAAGmZjVEwAAAADAAAAFAAAAEgAAAA4AAAAOAAIAGQCAb6WRGwAAABrZmRBVAAAAAR42s2TQQ6AIAwEOXgxEv//XEvCmmK97Zg4F8L0AC1L60F78kN5BtuEkkewCEB+0f8elKIpx+W1klKFMXBCitdBm5JEQ1ADhMxBKwVTkpTHAmQOGCVzYJdDAamA3Z/NlGXjyQth3xPxgCBbFQAAABpmY1RMAAAABQAAABQAAABEAAAANAAAADwACABkAAHaykukAAAAc2ZkQVQAAAAGeNq1lDEKwDAMAz10KS39/3MrDxIu2SrllphzII5xUjcocAKtpmRwgN5wgYR8QMuaBCQPUPGmZOEdq3hTdsBmMJmQJCl5CTXZlDtYGhyWSphykpI9tEvWlDsKnoP7OfSn5FDNB+bKiT4AT76lBBmhtuxsJwAAABh0RVh0U29mdHdhcmUAZ2lmMmFwbmcuc2YubmV0lv8TyAAAAABJRU5ErkJggg==");
        sc_pf.put("javaArgs", "-Xmx3G -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M");
        sc_pf.put("lastUsed", JSONObject.NULL);
        sc_pf.put("lastVersionId", "1.8.9-forge1.8.9-11.15.1.2318-1.8.9");
        sc_pf.put("name", "SkyClient");
        sc_pf.put("type", "custom");

        profilesjson.getJSONObject("profiles").put("SkyClient",sc_pf);

        try (FileWriter file = new FileWriter(folderloc + "/launcher_profiles.json")) {
            file.write(profilesjson.toString(4));
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Do you want to install any texture packs?");

        op = new Scanner(System.in);

        System.out.print("[y/n]:");

        String pcks = op.nextLine();

        if (pcks.equals("no") || pcks.equals("n")) {
            FileUtils.deleteDirectory(sctmp);
            System.out.println("Installed SkyClient");
            System.exit(0);
        }

        arry = new JSONArray(FileUtils.readFileToString(new File(cd + "/sc_tmp/files/packs.json"),"UTF-8"));

        for (int i = 0; i < (arry.length()); i++) {

            JSONObject obj = arry.getJSONObject(i);
            String dpname = obj.get("display").toString();
            String filenm = obj.get("file").toString();
            String id = obj.get("id").toString();

            clearScreen();

            System.out.println("Do you want to install "+ dpname +" ? ("+ filenm +")");

            op = new Scanner(System.in);

            System.out.print("[y/n]:");

            String txtslc = op.nextLine();
            if (txtslc.equals("n") || txtslc.equals("no")) {
                txtp.add(false);
            }else{
                txtp.add(true);
            }
        }

        slctednms = new ArrayList<>();

        for (int i = 0; i < (txtp.size()); i++) {
            if (txtp.get(i)) {

                JSONObject obj = arry.getJSONObject(i);
                String nme = obj.get("file").toString();

                slctednms.add(nme);
            }
        }

        System.out.println("Is this Correct?");
        System.out.println("");
        System.out.println(String.join(" , ",slctednms));
        System.out.println("");

        op = new Scanner(System.in);

        System.out.print("[y/n]:");

        crrct = op.nextLine();

        if (crrct.equals("no") || crrct.equals("n")) {
            FileUtils.deleteDirectory(sctmp);
            System.out.println("Installed SkyClient");
            System.exit(0);
        }

        for (int i = 0; i < (txtp.size()); i++) {
            if (txtp.get(i)) {

                JSONObject obj = arry.getJSONObject(i);
                String dpname = obj.get("display").toString();
                String filenm = obj.get("file").toString();


                System.out.println("Started Downloading "+dpname+" ("+filenm+")");

                try {
                    String url = obj.get("url").toString();

                    Download(url,folderloc + "/skyclient/resourcepacks/" + filenm);
                } catch (Exception e) {
                    FileUtils.moveFile(new File(cd + "/sc_tmp/files/packs/"+filenm),new File(folderloc + "/skyclient/resourcepacks/" + filenm));
                }

                System.out.println("Downloaded "+ dpname +" ("+filenm+")");
                System.out.println("");

            }
        }

        FileUtils.deleteDirectory(sctmp);
        System.out.println("Installed SkyClient");

    }

    public static void Download(String URL, String Loc) throws IOException {
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
    }

    public static void downloadrepo(String repo,String destination){
        try {
            System.out.println("Downloading Temp files");
            Git.cloneRepository()
                    .setURI(repo)
                    .setDirectory(Paths.get(destination).toFile())
                    .call();
            System.out.println("Downloaded Temp files");
        } catch (GitAPIException e) {

            e.printStackTrace();
        }
    }

    public static void clearScreen() {
        System.out.print("\033\143");
    }

}
