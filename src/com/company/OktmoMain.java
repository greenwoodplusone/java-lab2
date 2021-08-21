package com.company;

import java.util.regex.*;

public class OktmoMain {
    final public static String ONE_REG = "^[А-Я].?ово$";

    //\\S заменить на . если хотим учитывать названия из  нескольки слов
    final public static String TWO_REG = "^([^ауоиэыяюеё])\\S*\\1$";

    final public static String REG =
            "(\\d\\d).*?(\\d\\d\\d).*?(\\d\\d\\d).*?(?!000)(\\d\\d\\d).*?([a-я]+) ([a-я- \\d()]+)";

    public static void main(String[] args) {
        OktmoData place = new OktmoData();
//        new OktmoReader().readPlaces("data-20210701-structure-20150128.csv", place);
        new OktmoReader().readPlacesReg("data-20210701-structure-20150128.csv", place);

//        place.printAllPlaces(); // Показ несортированного списка
        place.printSortedPlaces(); // Показ сортированного списка

        System.out.println("\nСписок статусов:\n" + place.allStatuses()); // Вывод списка статусов

        System.out.println("\nСписка статусов с колличеством:\n" + OktmoAnalyzer.mapStatuses(place.getAllStatuses())); // Вывод списка статусов с колличеством

        System.out.println("\nВсего населенных пунктов:\n" + place.getCountPlaces()); // Вывод кол-во найденных пунктов


        // РЕГУЛЯРНЫЕ ВЫРАЖЕНИЯ
        System.out.println("\nСписок всех НП, название которых содержит меньше 6 букв и заканчиваются на -ово:\n" +
                OktmoAnalyzer.placesGivenTheExpression(place, ONE_REG));

        System.out.println("\nСписок всех НП, которые начинаются и заканчиваются га одну и ту же согласную букву:\n" +
                OktmoAnalyzer.placesGivenTheExpression(place, TWO_REG, true));
    }
}
