package com.company;

import java.util.regex.*;

public class OktmoMain {
    final private static String ONE_REG = "^[А-Я].?ово$";

    public static void main(String[] args) {
        OktmoData place = new OktmoData();
        new OktmoReader().readPlaces("data-20210701-structure-20150128.csv", place);

        //place.printAllPlaces(); // Показ несортированного списка
        place.printSortedPlaces(); // Показ сортированного списка

        System.out.println("\nСписок статусов:\n" + place.allStatuses()); // Вывод списка статусов

        System.out.println("\nСписка статусов с колличеством:\n" + OktmoAnalyzer.mapStatuses(place.getAllStatuses())); // Вывод списка статусов с колличеством

        System.out.println("\nВсего населенных пунктов:\n" + place.getCountPlaces()); // Вывод кол-во найденных пунктов


        // РЕГУЛЯРНЫЕ ВЫРАЖЕНИЯ
        System.out.println("\nСписок всех НП, название которых содержит меньше 6 букв и заканчиваются на -ово:\n" +
                OktmoAnalyzer.placesGivenTheExpression(place, ONE_REG));
    }
}
