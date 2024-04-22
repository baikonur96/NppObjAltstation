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
import java.util.Arrays;
import java.util.List;

public class Service {

    public static String parsefile(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path),Charset.forName("windows-1251"));
            lines.forEach(line -> builder.append(line + "\n"));
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(builder.length());
        return builder.toString();
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
