package org.example;

import java.util.ArrayList;
import java.util.List;

public class Npp {
    String nameNpp;
    List<Model> listModelNpp = new ArrayList<>();


    public String getNameNpp() {
        return nameNpp;
    }

    public void setNameNpp(String nameNpp) {
        this.nameNpp = nameNpp;
    }

    public List<Model> getListModelNpp() {
        return listModelNpp;
    }

    public void setListModelNpp(List<Model> listModelNpp) {
        this.listModelNpp = listModelNpp;
    }

    public void addListModelNpp(Model model) {
        listModelNpp.add(model);
    }
}
