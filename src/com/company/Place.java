package com.company;

public class Place  implements Comparable<Place>{

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

    @Override
    public int compareTo(Place place){
        return this.getname().compareTo(place.getname());

//        if (this.name.charAt(0) > place.name.charAt(0))
//            return 1;
//        else if (this.name.charAt(0) == place.name.charAt(0))
//            return 0;
//        else
//            return -1;
    }
}
