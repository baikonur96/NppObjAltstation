package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Main {
    public static void main(String[] args) {
        String nameObj = "obj_Station_stat.svg";
       String nppFile = Service.parsefile("C:\\Users\\Baiko\\NppObjAltstation\\data\\10DMB10EJ101.svg", nameObj);
       Document doc = Jsoup.parse(nppFile);
       Elements el = doc.select("image[xlink:href=\"obj_Station_stat.svg\"]");
       el.forEach(element -> {
           System.out.println("----------------");
           String elOnClick = element.select("rt\\:event[type=\"OnClick\"]").text();
           int firstChar = elOnClick.indexOf("\"");
           int  secondChar = elOnClick.indexOf("\"", firstChar + 1);
           String kksClickNpp = elOnClick.substring(firstChar + 1, secondChar);
           System.out.println(kksClickNpp);
           //System.out.println(element.select("rt\\:event[type=\"OnClick\"]"));
           System.out.println(elOnClick);


           System.out.println("++++++++++++++++++++++++++++");
           String elStationName = element.select("rt\\:dyn[type=\"Station_Name\"]").attr("value");
           if (elStationName.isEmpty()){
               System.out.println("пустой");
               element.append("<rt:dyn type=\"Station_Name\" mode=\"constant\" value=\"&quot;0000#" + kksClickNpp + "&quot;\"/>");
           }
           else {
               element.select("rt\\:dyn[type=\"Station_Name\"]").attr("value").replace(elStationName, "\"0000#" + kksClickNpp + "\"");
           }

         //  System.out.println(element.select("rt\\:dyn[type=\"Station_Name\"]").attr("value"));
          //System.out.println(element);
           System.out.println("----------------");
       });
       Service.WriteSVG("10DMB10EJ101NW", doc);
    }
}