package com.company;

import java.util.*;

enum OKTMOLevel {
    REGION,
    DISTRICT_OR_CITY,
    VILLAGE_COUNCIL,
    PLACE
}

public class OKTMOGroup {
    private OKTMOLevel level;
    private String name;
    private Long code;

    // Вложенная группа
    private ArrayList<Place> places = new ArrayList<Place>();
    private ArrayList<OKTMOGroup> oktmoGroupInnerList = new ArrayList<OKTMOGroup>();
    private TreeMap<Long, TreeMap<Long, OKTMOGroup>> oktmoGroupInnerMap = new TreeMap<Long, TreeMap<Long, OKTMOGroup>>();

    public OKTMOGroup(OKTMOLevel level, String name, Long code) {
        this.level = level;
        this.name = name;
        this.code = code;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public Long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * Добавление группы
     */
    public void addOktmoGroupMap(OKTMOData data, Long code, OKTMOGroup pOktmoGroupMap) {
        data.getOktmoGroupMap().put(code, pOktmoGroupMap);
    }

    public void addOktmoGroupInnerList(OKTMOGroup oktmoGroup) {
        oktmoGroupInnerList.add(oktmoGroup);
    }

    public void addOktmoGroupInnerMap(Long code, TreeMap<Long, OKTMOGroup> pOktmoGroupInnerMap) {
        oktmoGroupInnerMap.put(code, pOktmoGroupInnerMap);
    }

    public TreeMap<Long, TreeMap<Long, OKTMOGroup>> getOktmoGroupInnerMap() {
        return oktmoGroupInnerMap;
    }

    /**
     * Добавление населенных пунктов
     *
     * @param place
     */
    public void addPlace(Place place) {
        places.add(place);
    }

    public ArrayList<OKTMOGroup> getOktmoGroupInnerList() {
        return oktmoGroupInnerList;
    }
}
