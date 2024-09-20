package org.example;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static Map<String,Integer> rul = new HashMap<>();
    public static Map<String,List<String>> alt = new HashMap<>();

    public static void main(String[] args) {
        String folderPath = "data\\NPP_models\\";
        String nameObj = "obj_Station_stat.svg";
        String dbBin = "C:\\Users\\Adminsvu\\Documents\\NppObj\\data\\DB\\PLS_BIN_CONF.dmp";
        String alt = "C:\\Users\\Adminsvu\\Documents\\NppObj\\data\\DB\\altStation.dic";
        StringBuilder nppLogs = new StringBuilder();

        rul = UpdateBin(dbBin);
        System.out.println("OK_BIN: " + rul.size());
        Main.alt = UpdateAlt(alt);
        System.out.println("OK_ALT: " + Main.alt.size());


        int start = 0;
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        int totalFiles = files.length;
        int i = 0;
        StatProcess statProcess = new StatProcess(i, totalFiles);
        Thread thread = new Thread(statProcess);
        thread.setDaemon(true);
        thread.start();
        if (files != null) {
            for (File file : files) {
                i++;
                statProcess.setI(i);
                if (file.isFile() && file.getName().endsWith(".svg")) {
                    String nppFile = Service.parsefile(file.toString(), nameObj);
                    if (!nppFile.isEmpty()) {
                        Service.WriteFile(file.getName(), nppFile);
                    }
                }
            }
        }
        for (Map.Entry<String, Integer> entry : Service.nppStat.entrySet()){
            nppLogs.append(++start + " - " + entry.getKey() + " - " + entry.getValue() + "\n");
        }
        Service.WriteFile("file.log", nppLogs.toString());

//
//        String nameObj = "obj_Station_stat.svg";
//       String nppFile = Service.parsefile("data\\10DMB20EJ101.svg", nameObj);


/*       Document doc = Jsoup.parse(nppFile);
       Elements el = doc.select("image[xlink:href=\"obj_Station_stat.svg\"]");
       el.forEach(element -> {
           System.out.println("----------------");
           String elOnClick = element.select("rt\\:event[type=\"OnClick\"]").text();
           int firstChar = elOnClick.indexOf("\"");
           int  secondChar = elOnClick.indexOf("\"", firstChar + 1);
           String kksClickNpp = elOnClick.substring(firstChar + 1, secondChar);
           System.out.println(kksClickNpp);
           //System.out.println(element.select("rt\\:event[type=\"OnClick\"]"/
               element.append("<rt:dyn type=\"Station_Name\" mode=\"constant\" value=\"&quot;0000#" + kksClickNpp + "&quot;\"/>");
           }
           else {
               element.select("rt\\:dyn[type=\"Station_Name\"]").attr("value").replace(elStationName, "\"0000#" + kksClickNpp + "\"");
           }

         //  System.out.println(element.select("rt\\:dyn[type=\"Station_Name\"]").attr("value"));
          //System.out.println(element);
           System.out.println("----------------");
       });
       Service.WriteSVG("10DMB10EJ101NW", doc);*/
    }

    private static Map UpdateBin(String db){
        Map<String, Integer> kks = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(db), Charset.forName("Windows-1251"));
            for (String line : lines) {
                String[] fragments = line.split("\\|");
                if (fragments.length < 42 || fragments[0].contains("#") ) {
                    continue;
                }


                kks.put(fragments[42], Integer.valueOf(fragments[6]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return kks;
    }

    private static Map UpdateAlt(String alt){
        Map<String, List<String>> nppKks = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(alt), Charset.forName("Windows-1251"));
            for (String line : lines) {
                String[] fragments = line.split("\t");
                if (fragments.length < 2) {
                    continue;
                }
                if (nppKks.containsKey(fragments[1])) {
                    nppKks.get(fragments[1]).add(fragments[0]);
                }
                else{
                    List<String> forMap = new ArrayList<>();
                    forMap.add(fragments[0]);
                    nppKks.put(fragments[1], forMap);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return nppKks;
    }
}