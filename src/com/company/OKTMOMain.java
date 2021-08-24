package com.company;

import org.junit.jupiter.api.Test;

public class OKTMOMain {
    final public static String ONE_REG = "^[А-Я].?ово$";

    //\\S заменить на . если хотим учитывать названия из  нескольки слов
    final public static String TWO_REG = "^([^ауоиэыяюеё])\\S*\\1$";

    // регулярное выражение для поиска НП
    final public static String PLACE_REG =
            "\"(\\d\\d)\";\"(\\d\\d\\d)\";\"(\\d\\d\\d)\";\"(?!000)(\\d\\d\\d)\";\"\\d\";\"\\d\";\"(([1-9-/.а-я]+)\\s)?(((\"*)[^;\"]+\\9)+)\"";

    final public static String GROUP_REG =
            "\"(\\d\\d)\";\"(\\d\\d\\d)\";\"(\\d\\d\\d)\";\"(000)\";\"\\d\";\"\\d\";\"(((\"*)[^;\"]+\\7)+)\"";

    public static void main(String[] args) {
        OKTMOData place = new OKTMOData();
//        new OktmoReader().readPlaces("data-20210701-structure-20150128.csv", place);
        new OKTMOReader().readPlacesReg("data-20210701-structure-20150128.csv", place);

//        place.printAllPlaces(); // Показ несортированного списка
        place.printSortedPlaces(); // Показ сортированного списка

        System.out.println("\nСписок статусов:\n" + place.allStatuses()); // Вывод списка статусов

        System.out.println("\nСписка статусов с колличеством:\n" + OKTMOAnalyzer.mapStatuses(place.getAllStatuses())); // Вывод списка статусов с колличеством

        System.out.println("\nВсего населенных пунктов:\n" + place.getCountPlaces()); // Вывод кол-во найденных пунктов


        // РЕГУЛЯРНЫЕ ВЫРАЖЕНИЯ
        System.out.println("\nСписок всех НП, название которых содержит меньше 6 букв и заканчиваются на -ово (" +
                OKTMOAnalyzer.getPlacesGivenTheExpression(place, ONE_REG)  +
                "\n" + OKTMOAnalyzer.getPlacesGivenTheExpression().size() + " шт.");

        System.out.println("\nСписок всех НП, которые начинаются и заканчиваются на одну и ту же согласную букву:\n" +
                OKTMOAnalyzer.getPlacesGivenTheExpression(place, TWO_REG, true) +
                        "\n" + OKTMOAnalyzer.getPlacesGivenTheExpression().size() + " шт.");
    }

    @Test
    public void placesGroup() {
        OKTMOData placeGroup = new OKTMOData();
        new OKTMOReader().readPlacesGroup("data-20210701-structure-20150128.csv", placeGroup);

        placeGroup.printCountGroup();
    }
}
