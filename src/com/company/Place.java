package com.company;

public class Place {

    private long code;
    private String status;
    private String name;

    public Place(long code, String status, String name) {
        this.code = code;
        this.status = status;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Код = " + code + ", имя = " + name + ", статус = " + status + ";";
    }

    public String getname() {
        return name;
    }

    public long getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}
