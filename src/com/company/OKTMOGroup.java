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
    private ArrayList<OKTMOGroup> oktmoGroupList;

    private ArrayList<Place> places;

    public OKTMOGroup(OktmoLevel level, String name, Long code) {
        this.level = level;
        this.name = name;
        this.code = code;
        this.oktmoGroupList = new ArrayList<OKTMOGroup>();
        this.places = new ArrayList<Place>();
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
    public void addOKTMOGroup (OKTMOGroup oktmoGroup) {
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
