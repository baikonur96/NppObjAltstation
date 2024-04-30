package org.example;

import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {
    public static Map<String, Integer> nppStat = new LinkedHashMap<>();

    public static String parsefile(String path, String obj) {
        boolean emptyNpp = true;
        String regexStartModel = "\\W*<image.*xlink:href=\"" + obj + "\".*";
        String regexEndModel = "\\W*</image>";
        Npp npp = new Npp();

        Pattern patternStartModel = Pattern.compile(regexStartModel);
        Pattern patternEndModel = Pattern.compile(regexEndModel);

        StringBuilder build = new StringBuilder();
        try {

            List<String> lines = Files.readAllLines(Paths.get(path),Charset.forName("windows-1251"));

            for (int i = 0; i < lines.size(); i++){
                Matcher matcherStartModel = patternStartModel.matcher(lines.get(i));
                if (matcherStartModel.matches()){
                    emptyNpp = false;
                    if (nppStat.containsKey(path)){
                        int o = nppStat.get(path);
                        nppStat.put(path, o + 1);
                    }else {
                        nppStat.put(path, 1);
                    }

                    Model model = new Model();
                    model.setName(obj);
                    model.setStartInd(i);
                    model.addLineModel(lines.get(i));
                    npp.setNameNpp(path);
                 //   npp.setListModelNpp(model);
                    StringBuilder modelText = new StringBuilder();
                    modelText.append(lines.get(i) + "\n");
                        for (int y = i + 1; y < lines.size(); y++){
                            Matcher matcherEndModel = patternEndModel.matcher(lines.get(y));
                            if (matcherEndModel.matches()){

                               modelText.append(lines.get(y) + "\n");
                               model.addLineModel(lines.get(y));
                                model.setText(modelText.toString());
                                build.append(parseModel(model));
                                i = y;
                                break;
                            }
                            model.addLineModel(lines.get(y));
                            modelText.append(lines.get(y) + "\n");
                        }
                }else {
                    build.append(lines.get(i) + "\n");
                }


            }


        }catch (Exception e){
            e.printStackTrace();
        }
        if (emptyNpp){
            return "";
        }

        return build.toString();
    }

    public static String parseModel(Model model) {
        String regexOnClick = ".*\"OnClick\">LoadNew\\(&quot;(\\w{5,})&quot;\\).*";
        String regexStationName = ".*\"Station_Name\".*value=\"&quot;(.*)&quot;\".*";
        Pattern patternOnClick = Pattern.compile(regexOnClick);
        Pattern patternStationName = Pattern.compile(regexStationName);
        StationName stationName = new StationName("","","");
        model.setStationName(stationName);
        model.setOnClick("");
        for (int i = 0; i < model.getLineModel().size(); i++) {
            Matcher matcherOnClick = patternOnClick.matcher(model.getLineModel().get(i));
            Matcher matcherStationName = patternStationName.matcher(model.getLineModel().get(i));
            if  (matcherOnClick.find(1)){
                model.setOnClick(matcherOnClick.group(1).trim());
              //  System.out.println(matcherOnClick.group(1));
            }
            if  (matcherStationName.find(1)){
                String regexUsersNpp = "(\\w{4})#(\\w{10,})";
                Pattern patternUsersNpp = Pattern.compile(regexUsersNpp);
                Matcher matcherUsersNpp = patternUsersNpp.matcher(matcherStationName.group(1));

                stationName.setId(i);
                stationName.setText(matcherStationName.group(1));
           //     System.out.println(matcherStationName.group(1));
                if (matcherUsersNpp.find()){
                    stationName.setUsers(matcherUsersNpp.group(1));
                    stationName.setNameNpp(matcherUsersNpp.group(2));
                 //   System.out.println(matcherUsersNpp.group(1) + " - " + matcherUsersNpp.group(2));
                }
            }

        }

        if (model.getOnClick().isEmpty()){
        //    System.out.println(model.getLineModel().size());
        //    System.out.println(model.getText().length() + " - LEGHTOUT");
            return model.getText();
        }
         else if (model.getStationName().getText().isEmpty()){
             String textAdd = "    <rt:dyn type=\"Station_Name\" mode=\"constant\" value=\"&quot;0000#" + model.getOnClick() + "&quot;\"/>";
            model.getLineModel().add(model.getLineModel().size() - 2, textAdd);
            model.writeToText();
         //   System.out.println(model.getText().length() + " - LEGHTOUT");
        //    System.out.println(model.getLineModel().size());
            return model.getText();
        }
         else if (model.getOnClick().equals(model.getStationName().getNameNpp())){
             if (model.getStationName().getUsers().equals("0000")){
              //   System.out.println(model.getLineModel().size());
               //  System.out.println(model.getText().length() + " - LEGHTOUT");
                 return model.getText();
             }else {
                 model.getStationName().setUsers("0000");
                 model.getStationName().setNameNpp(model.getOnClick());
                 String textAdd = "    <rt:dyn type=\"Station_Name\" mode=\"constant\" value=\"&quot;" + model.getStationName().getUsers() + "#" + model.getStationName().getNameNpp() + "&quot;\"/>";
                 int removeId = model.getStationName().getId();
                 model.getLineModel().remove(removeId);
                 model.getLineModel().add(removeId, textAdd);
                 model.writeToText();
               //  System.out.println(model.getText().length() + " - LEGHTOUT");
                // System.out.println(model.getLineModel().size());
                 return model.getText();

             }

        }
         else {
             model.getStationName().setUsers("0000");
             model.getStationName().setNameNpp(model.getOnClick());
            String textAdd = "    <rt:dyn type=\"Station_Name\" mode=\"constant\" value=\"&quot;" + model.getStationName().getUsers() + "#" + model.getStationName().getNameNpp() + "&quot;\"/>";
            int removeId = model.getStationName().getId();
            model.getLineModel().remove(removeId);
            model.getLineModel().add(removeId, textAdd);
            model.writeToText();
          //  System.out.println(model.getLineModel().size());
        //    System.out.println(model.getText().length() + " - LEGHTOUT");
            return model.getText();

        }
       // return model.getText();
    }



    public static void WriteFile(String name, String body) {
        String path = "data\\reb\\" + name;
        try {
            PrintWriter writer = new PrintWriter(path, Charset.forName("windows-1251"));
            writer.write(body);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void WriteSVG(String name, Document doc) {
//        String path = "C:\\Users\\Baiko\\NppObjAltstation\\data\\reb\\" + name + ".svg";
//        try {
//            File f = new File(path);
//            FileUtils.writeStringToFile(f, doc.html(), Charset.forName("windows-1251") );
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


//    public static  void  saveSvg (String code){
//        String documentPath = "save-to-SVG.svg";
//
//
//
//// Initialize an SVG instance from the content string
//        com.aspose.html.dom.svg.SVGDocument document = new com.aspose.html.dom.svg.SVGDocument(code, ".");
//
//// Save the SVG file to a disk
//        document.save(documentPath);
//    }
}
