package org.example;

public class StationName {
    String users;
    String nameNpp;
    String text;
    int id;

    public StationName(String users, String nameNpp, String text) {
        this.users = users;
        this.nameNpp = nameNpp;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getNameNpp() {
        return nameNpp;
    }

    public void setNameNpp(String nameNpp) {
        this.nameNpp = nameNpp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
