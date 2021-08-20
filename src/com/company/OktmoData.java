package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class OktmoData{

    private ArrayList<Place> places;
    private HashSet<String> allStatuses;
    private ArrayList<Place> sortedPlaces;

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

    /**
     * Получение имени насленного пункта по номеру в списке
     *
     * @param number
     * @return
     */
    public String getNamePlace(int number) {
        if (number > places.size()) {
            System.out.println("Превышено количество населенных пунктов");
        }
        return places.get(number).getname();
    }

    /**
     * Получение кода насленного пункта по номеру в списке
     *
     * @param number
     * @return
     */
    public Long getCodePlace(int number) {
        if (number > places.size()) {
            System.out.println("Превышено количество населенных пунктов");
        }
        return places.get(number).getCode();
    }

    /**
     * Получение статуса насленного пункта по номеру в списке
     *
     * @param number
     * @return
     */
    public String getStatusPlace(int number) {
        if (number > places.size()) {
            System.out.println("Превышено количество населенных пунктов");
        }
        return places.get(number).getStatus();
    }

    /**
     * Получение количества насленных пунктов
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
    public String allStatuses() {
        return allStatuses.toString();
    }

    /**
     * Вывод всего списка населенных пунктов
     */
    public void printAllPlaces() {
        for (Place thisPlace : places) {
            System.out.println(thisPlace.toString());
        }
    }

    /**
     * Получение списка статусов
     *
     * @return
     */
    public HashSet<String> getAllStatuses() {
        return allStatuses;
    }

    /**
     * Создание нового отсортированного списка с населенными пунктами
     *
     * @return
     */
    public ArrayList<Place> sortPlaces() {
        sortedPlaces = new ArrayList<Place>(places);
        Collections.sort(sortedPlaces);

        return sortedPlaces;
    }

    /**
     * Вывод отсортированного списка с населенными пунктами
     */
    public void printSortedPlaces() {
        sortPlaces();

        for (Place thisPlace : sortedPlaces) {
            System.out.println(thisPlace.toString());
        }
    }

    /**
     * Получение списка населенных пунктов
     *
     * @return
     */
    public ArrayList<Place> getPlaces() {
        return places;
    }
}
