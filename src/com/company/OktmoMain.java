package com.company;

public class OktmoMain {

    public static void main(String[] args) {
        OktmoData place = new OktmoData();
        new OktmoReader().readPlaces("data-20210701-structure-20150128.csv", place);

        place.printAllPlaces();

        System.out.println(place.printAllStatuses());
        System.out.println("Всего населенных пунктов: " + place.getCountPlaces());
    }
}
