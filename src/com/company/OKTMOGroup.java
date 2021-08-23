package com.company;

import java.util.*;

enum OktmoLevel {
    REGION,
    DISTRICT_OR_CITY,
    VILLAGE_COUNCIL,
    PLACE
}

public class OKTMOGroup {
    private OktmoLevel level;
    private String name;
    private Long code;

    // Вложенная группа
    private ArrayList<Place> places = new ArrayList<Place>();
    private ArrayList<OKTMOGroup> oktmoGroupList = new ArrayList<OKTMOGroup>();

    public OKTMOGroup(OktmoLevel level, String name, Long code) {
        this.level = level;
        this.name = name;
        this.code = code;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public String getName() {
        return name;
    }

    /**
     * Добавление группы
     *
     * @param oktmoGroup
     */
    public void addOktmoGroupMap(OktmoData data, Long code, OKTMOGroup oktmoGroup) {
        data.getOktmoGroupMap().put(code, oktmoGroup);
    }

    public void addOktmoGroupList(OKTMOGroup oktmoGroup) {
        oktmoGroupList.add(oktmoGroup);
    }

    /**
     * Добавление населенных пунктов
     *
     * @param place
     */
    public void addPlace(Place place) {
        places.add(place);
    }

    public ArrayList<OKTMOGroup> getOktmoGroupList() {
        return oktmoGroupList;
    }
}
