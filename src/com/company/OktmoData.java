package com.company;

import java.util.*;

public class OktmoData{

    private ArrayList<Place> places;
    private HashSet<String> allStatuses;
    private ArrayList<Place> sortedPlaces;

    private ArrayList<OKTMOGroup> oktmoGroupList = new ArrayList<OKTMOGroup>();
    private TreeMap<Long, OKTMOGroup> oktmoGroupMap = new TreeMap<Long, OKTMOGroup>();
    private TreeMap<Long,Place> placeMap;

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

    public void addOktmoGroupMap(Long code, OKTMOGroup oktmoGroup) {
        oktmoGroupMap.put(code, oktmoGroup);
    }

    public void addOktmoGroupList(OKTMOGroup oktmoGroup) {
        oktmoGroupList.add(oktmoGroup);
    }

    public void printCountGroup() {
        System.out.println("Количество регионов: " + oktmoGroupList.size());

        for (OKTMOGroup oktmoGroupRegion : oktmoGroupList) {
            System.out.println("|___ Регион \"" + oktmoGroupRegion.getName() + "\" включает: "  +
                    oktmoGroupRegion.getOktmoGroupList().size() + " района или города");

            for (OKTMOGroup oktmoGroupDistrictOrCity : oktmoGroupRegion.getOktmoGroupList()) {
                System.out.println("|___\n|___ |___ Район или город \"" + oktmoGroupDistrictOrCity.getName() +
                        "\" включает: "  + oktmoGroupDistrictOrCity.getOktmoGroupList().size() + " сельсоветов");


                for (OKTMOGroup oktmoVillageCouncil : oktmoGroupDistrictOrCity.getOktmoGroupList()) {
                    System.out.println("|___\n|___ |___\n|___ |___ |___ Cельсовет \"" + oktmoVillageCouncil.getName() +
                            "\" включает: "  + oktmoVillageCouncil.getOktmoGroupList().size() + " НП");
                }
            }
        }
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
