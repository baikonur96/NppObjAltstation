package org.example;

import java.util.ArrayList;
import java.util.List;

public class Model {
    String Name;
    String text;
    List<String> lineModel = new ArrayList<>();
    int startInd;
    String title;
    String label;
    String onClick;
    StationName stationName;

    public void writeToText() {
        StringBuilder builder = new StringBuilder();
        for (String line : lineModel){
            builder.append(line + "\n");
        }
        this.setText(builder.toString());
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getLineModel() {
        return lineModel;
    }

    public void setLineModel(List<String> lineModel) {
        this.lineModel = lineModel;
    }

    public int getStartInd() {
        return startInd;
    }

    public void setStartInd(int startInd) {
        this.startInd = startInd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOnClick() {
        return onClick;
    }

    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    public StationName getStationName() {
        return stationName;
    }

    public void setStationName(StationName stationName) {
        this.stationName = stationName;
    }

    public void addLineModel(String line) {
        lineModel.add(line);
    }
}
