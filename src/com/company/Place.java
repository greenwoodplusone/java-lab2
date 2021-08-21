package com.company;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return code == place.code &&
                status.equals(place.status) &&
                name.equals(place.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, status, name);
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
