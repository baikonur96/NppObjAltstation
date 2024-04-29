package org.example;

import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {


    public static String parsefile(String path, String obj) {
        String regexStartModel = "\\W*<image.*xlink:href=\"" + obj + "\".*";
        String regexEndModel = "\\W*</image>";
        List<Model> listModel = new ArrayList<>();


      //  String regex = "<image.*xlink:href=\"obj_Station_stat\\.svg\"";
        Pattern patternStartModel = Pattern.compile(regexStartModel);
        Pattern patternEndModel = Pattern.compile(regexEndModel);
        StringBuilder build = new StringBuilder();
        try {

            List<String> lines = Files.readAllLines(Paths.get(path),Charset.forName("windows-1251"));

            for (int i = 0; i < lines.size(); i++){
                Matcher matcherStartModel = patternStartModel.matcher(lines.get(i));
                if (matcherStartModel.matches()){
                    Model model = new Model();
                    model.setName(obj);
                    StringBuilder builder = new StringBuilder();
                  //  flag = true;
                    builder.append(lines.get(i) + "\n");
                        for (int y = i + 1; y < lines.size(); y++){
                            Matcher matcherEndModel = patternEndModel.matcher(lines.get(y));
                            if (matcherEndModel.matches()){
                                builder.append(lines.get(y) + "\n");
                                model.setText(builder.toString());
                                listModel.add(model);
                                break;
                            }
                            builder.append(lines.get(y) + "\n");
                        }
                }


            }
//            lines.forEach(line -> {
//                Matcher matcherEndModel = patternEndModel.matcher(line);
//                Matcher matcherStartModel = patternStartModel.matcher(line);
//                if (matcherEndModel.matches()){
//                    builder.append(line + "\n");
//                    flag = false;
//                }
//
//                else if (flag == true){
//                    builder.append(line + "\n");
//
//                }
//                else if (matcherStartModel.matches()){
//                    flag = true;
//                    builder.append(line + "\n");
//
//                }
//
//                } );
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(path + " - " + listModel.size());
        return build.toString();
    }

    public static void WriteFile(String name, String body) {
        String path = "C:\\Users\\Baiko\\NppObjAltstation\\data\\reb\\" + name + ".svg";
        try {
            PrintWriter writer = new PrintWriter(path);
            writer.write(body);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void WriteSVG(String name, Document doc) {
        String path = "C:\\Users\\Baiko\\NppObjAltstation\\data\\reb\\" + name + ".svg";
        try {
            File f = new File(path);
            FileUtils.writeStringToFile(f, doc.html(), Charset.forName("windows-1251") );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
