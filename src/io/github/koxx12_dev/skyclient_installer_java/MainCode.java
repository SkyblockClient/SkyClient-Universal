package io.github.koxx12_dev.skyclient_installer_java;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainCode extends Utils {

    public void code(List<String> mods, List<String> packs, String mc) throws IOException, URISyntaxException {

        Map<String, String> modidToUrl = new HashMap<>();
        Map<String, String> packidToUrl = new HashMap<>();
        Map<String, String> modidToFile = new HashMap<>();
        Map<String, String> packidToFile = new HashMap<>();

        String path = MainGui.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, "UTF-8");

        String modsrq = request("https://raw.githubusercontent.com/nacrt/SkyblockClient-REPO/main/files/mods.json");
        String packsrq = request("https://raw.githubusercontent.com/nacrt/SkyblockClient-REPO/main/files/packs.json");

        JSONArray packsjson = new JSONArray(packsrq);
        JSONArray modsjson = new JSONArray(modsrq);

        modsjson.remove(modsjson.length()-1);

        //System.out.println(decodedPath);

        File verMcForge = new File(mc+"/versions/1.8.9-forge1.8.9-11.15.1.2318-1.8.9");
        File scMc = new File(mc+"/skyclient");
        File scMcCfg = new File(mc+"/skyclient/config");
        File scMcMods = new File(mc+"/skyclient/mods");
        File scMcRP = new File(mc+"/skyclient/resourcepacks");

        verMcForge.mkdir();
        scMc.mkdir();
        scMcCfg.mkdir();
        scMcMods.mkdir();
        scMcRP.mkdir();

        Download("https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/forge/1.8.9-forge1.8.9-11.15.1.2318-1.8.9.json",mc+"/versions/1.8.9-forge1.8.9-11.15.1.2318-1.8.9/1.8.9-forge1.8.9-11.15.1.2318-1.8.9.json");

        if (!new File(mc).exists()) {
            JOptionPane.showMessageDialog(null, "Failed to detect \""+mc+"\"\nExiting", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        System.out.println(mc+" exists");
        if (!new File(mc+"/versions/1.8.9").exists()) {
            JOptionPane.showMessageDialog(null, "Failed to detect \""+mc+"\"\nExiting", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        if (mods.contains("itlt")) {
            //Utility Bundle

            mods.add("scrollabletooltips");

            new File(mc+"/skyclient/config/itlt").mkdir();

            Download("https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/config/icon.png",mc+"/skyclient/config/itlt/icon.png");
            Download("https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/config/itlt.cfg",mc+"/skyclient/config/itlt.cfg");

        }
        if (mods.contains("cmm")) {
            //GUI Bundle

            mods.add("smoothscrollingeverywhere");

            new File(mc+"/skyclient/config/CustomMainMenu").mkdir();

            Download("https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/config/play.json",mc+"/skyclient/config/CustomMainMenu/play.json");
            Download("https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/config/mainmenu.json",mc+"/skyclient/config/CustomMainMenu/mainmenu.json");

        }
        if (mods.contains("antidreamskin")) {
            //QOL Bundle

            mods.add("popup_events");
            mods.add("tnttime");

        }

        for (int i = 0; i < modsjson.length(); i++) {

            String id = (String) modsjson.getJSONObject(i).get("id");
            String url = "";
            String file = (String) modsjson.getJSONObject(i).get("file");

            if (id.equals("rpm")) {
                url = "https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/mods/Resource_Pack_Manager_1.2.jar";
            } else {
                url = (String) modsjson.getJSONObject(i).get("url");
            }

            modidToFile.put(id,file);
            modidToUrl.put(id,url);

        }

        for (int i = 0; i < packsjson.length(); i++) {

            Map<String, String> map = new HashMap<>();

            String id = (String) packsjson.getJSONObject(i).get("id");
            String url = "";
            String file = (String) packsjson.getJSONObject(i).get("file");

            try {
                url = (String) packsjson.getJSONObject(i).get("url");
            } catch (Exception e) {
                url = "https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/packs/"+urlEncode((String) packsjson.getJSONObject(i).get("file"));
            }

            packidToFile.put(id,file);
            packidToUrl.put(id,url);

        }


        for (String mod : mods) {

            String Name = modidToFile.get(mod);
            String Url = modidToUrl.get(mod);

            Download(Url, mc + "/skyclient/mods/" + Name);

            System.out.println("Downloaded: " + Url + " , " + Name);

        }

        for (String pack : packs) {

            String Name = packidToFile.get(pack);
            String Url = packidToUrl.get(pack);

            Download(Url, mc + "/skyclient/resourcepacks/" + Name);

            System.out.println("Downloaded: " + Url + " , " + Name);

        }

        JSONObject profilesjson = new JSONObject(FileUtils.readFileToString(new File(mc + "/launcher_profiles.json"),"UTF-8"));

        JSONObject sc_pf = new JSONObject();
        sc_pf.put("created", JSONObject.NULL);
        sc_pf.put("gameDir", mc + replaceWithOsPath("||skyclient"));
        sc_pf.put("icon", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAMAAAD04JH5AAAACGFjVEwAAAAEAAAAAHzNZtAAAAAzUExURf//ACOQY5HbaR68c////11dXXR0dIWFhc3fbNDq6T09PWG/5j+Y2KjQ2Sdyxtrq+k2b5npKrGcAAAABdFJOUwBA5thmAAAAGmZjVEwAAAAAAAAAgAAAAIAAAAAAAAAAAAAIAGQAAK+px9AAAAKZSURBVHja7ZvpbgIxDISBQrcFerz/0zYjZarp4F0OocYgz5898PEhWXESwmp1Rpuu1SgVQAqAl6Z7QNwUowCGAjA5tO6as/1o2nbN2cD/KogCSAHw1kSAyBkJ37u2IrdjISNeATwGAI0pFqFCeFJ9xr0mp28BPAaAFgyTR4ORJ8RgxIL0ePBjvALID8CEdGBTigqRTQjXpYZGv4uaUQEMBWASB4Cu+hZN9Hf4Rf8CGArAAUgHjY2In9Pm0ma2NhVAXgBNxORaOFpMDqBBdSLDGK4CyAegAw+LzZuIBtOEXMDqQiYqQC3yE4gCGA5AZx94IgB/1k0MHbAiAIUogDwAPvlgIUUTCW9IGlQ3tebswkIsgKEAvhDVhYgaewKfYCxBRfEKIDdAFMATfDYduw5NHlhjaAG6TQGMB9Aiigowak6HrqMIQOcAfGJaADkA9Mckbxo6yXBnbEyxALExOTW9NvkEdG5h8pu8AFIAvHbtm2iw69JnJCEUN6gBgPupC3G0APmecXgPuwLIB4AkgNBgfO8AKED+YIFBaBLBZjIhDu8RqwByAfAFr2qsACww3HMQggBAnyiGxqHNn5VxAQwHIAQKUAtPA3hgAqAIcaWv20UxCyA3AAPwfglAC3HfpXb6pXaicKe8AIYDQAxARzpPgTQ5RH9PHsUsgLwACqEAPpnAFQ2IC1QCeGNjMj/oUgC5AQhBZwb0SaoCQJE/Dzb5gTc/6FQAuQD8gJo2kQiAEEsHnVVnD0AXwFCAr6bogOrOhALTRhQBePF5zALIB6AfRgOIN6nvprmByAuRg4/GPWlKBTAcwJPqM5uHTjKRlBtUOiG5WQUwHEAhzk0cdZOqAJ4L4BqxOAGw9Ae3AnhOgGiy8a8QBXBPgB9G8drRaA0Z2QAAABpmY1RMAAAAAQAAABQAAAA8AAAANAAAADwACABkAAETQ/wcAAAAWmZkQVQAAAACeNq1kzEOACAIAx1cjMT/P9eSSCLqJOU2joVAKQOUk6BUOmBLRUAFLLkX2mTIbNg3u7ZEkExssS4UQZmZ1WcQPmXGf9rAbvCgNCELlrRwNRCUEzPhDNF8OvhOAAAAGmZjVEwAAAADAAAAFAAAAEgAAAA4AAAAOAAIAGQCAb6WRGwAAABrZmRBVAAAAAR42s2TQQ6AIAwEOXgxEv//XEvCmmK97Zg4F8L0AC1L60F78kN5BtuEkkewCEB+0f8elKIpx+W1klKFMXBCitdBm5JEQ1ADhMxBKwVTkpTHAmQOGCVzYJdDAamA3Z/NlGXjyQth3xPxgCBbFQAAABpmY1RMAAAABQAAABQAAABEAAAANAAAADwACABkAAHaykukAAAAc2ZkQVQAAAAGeNq1lDEKwDAMAz10KS39/3MrDxIu2SrllphzII5xUjcocAKtpmRwgN5wgYR8QMuaBCQPUPGmZOEdq3hTdsBmMJmQJCl5CTXZlDtYGhyWSphykpI9tEvWlDsKnoP7OfSn5FDNB+bKiT4AT76lBBmhtuxsJwAAABh0RVh0U29mdHdhcmUAZ2lmMmFwbmcuc2YubmV0lv8TyAAAAABJRU5ErkJggg==");
        sc_pf.put("javaArgs", "-Xmx3G -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M");
        sc_pf.put("lastUsed", JSONObject.NULL);
        sc_pf.put("lastVersionId", "1.8.9-forge1.8.9-11.15.1.2318-1.8.9");
        sc_pf.put("name", "SkyClient");
        sc_pf.put("type", "custom");

        profilesjson.getJSONObject("profiles").put("SkyClient",sc_pf);

        try (FileWriter file = new FileWriter(mc + "/launcher_profiles.json")) {
            file.write(profilesjson.toString(4));
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        File mf = new File(mc + "/libraries/net/minecraftforge");
        File f = new File(mc + "/libraries/net/minecraftforge/forge");
        File numbers = new File(mc + "/libraries/net/minecraftforge/forge/1.8.9-11.15.1.2318-1.8.9");
        mf.mkdir();
        f.mkdir();
        numbers.mkdir();

        Download("https://github.com/nacrt/SkyblockClient-REPO/raw/main/files/forge/forge-1.8.9-11.15.1.2318-1.8.9.jar",mc + "/libraries/net/minecraftforge/forge/1.8.9-11.15.1.2318-1.8.9/forge-1.8.9-11.15.1.2318-1.8.9.jar");

        JOptionPane.showMessageDialog(null, "Installed SkyClient", "Done", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);

    }
}
