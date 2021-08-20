package com.company;

import java.util.ArrayList;
import java.util.HashSet;

public class OktmoData {
    private ArrayList<Place> places;
    private HashSet<String> allStatuses;

    public OktmoData() {
        this.places = new ArrayList<Place>();
        this.allStatuses = new HashSet<String>();
    }

    /**
     * Добавление населенных пунктов
     *
     * @param place
     * @param status
     */
    public void addPlace(Place place, String status) {
        places.add(place);
        allStatuses.add(status);
    }

    public String getNamePlace(int number) {
        if (number > places.size()) {
            System.out.println("Превышено количество населенных пунктов");
        }
        return places.get(number).getname();
    }

    public Long getCodePlace(int number) {
        if (number > places.size()) {
            System.out.println("Превышено количество населенных пунктов");
        }
        return places.get(number).getCode();
    }

    public String getStatusPlace(int number) {
        if (number > places.size()) {
            System.out.println("Превышено количество населенных пунктов");
        }
        return places.get(number).getStatus();
    }

    /**
     * Получение колечества насленных пунктов
     *
     * @return
     */
    public int getCountPlaces() {
        return places.size();
    }

    /**
     * Получение списка всех статусов
     *
     * @return
     */
    public String printAllStatuses() {
        return allStatuses.toString();
    }

    /**
     * Вывод всех всего списка населенных пунктов
     */
    public void printAllPlaces() {
        for (Place thisPlace : places) {
            System.out.println(thisPlace.toString());
        }
    }
}
