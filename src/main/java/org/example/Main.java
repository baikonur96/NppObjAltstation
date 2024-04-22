package org.example;

import com.sun.javadoc.Doc;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Main {
    public static void main(String[] args) {
       String nppFile = Service.parsefile("");
       Document doc = Jsoup.parse(nppFile);
       Elements el = doc.select("image.obj_Station_stat");
       el.forEach(element -> {
           System.out.println("----------------");
           System.out.println(element.text());
           System.out.println("----------------");
       });
    }
}