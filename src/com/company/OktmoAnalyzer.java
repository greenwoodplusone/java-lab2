package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OktmoAnalyzer {
    private static HashMap<String, Integer> mapStatuses = new HashMap<String, Integer>();

    /**
     * Заполнение карты статусов
     *
     * @param status
     */
    public static void fillingMapStatuses(String status) {
        if (mapStatuses.containsKey(status)) {
            int countStatus = mapStatuses.get(status);
            mapStatuses.replace(status, (countStatus + 1));
        } else {
            mapStatuses.put(status, 1);
        }
    }

    /**
     * Вывод карты статусов
     *
     * @param allStatuses
     */
     public static HashMap<String, Integer> mapStatuses(HashSet<String> allStatuses) {
        return mapStatuses;
    }

    /**
     * Создание множетсва с фильтром по регулярному выражению
     *
     * @param place
     * @param regexr
     * @return
     */
    public static HashSet<String> placesGivenTheExpression(OktmoData place, String regexr) {
        return placesGivenTheExpression(place, regexr, false);
    }

    /**
     * Создание множетсва с фильтром по регулярному выражению (игнорирует регистр)
     *
     * @param place
     * @param regexr
     * @return
     */
    public static HashSet<String> placesGivenTheExpression(OktmoData place, String regexr, boolean ignoreСase) {
        HashSet<String> placesGivenTheExpression = new HashSet<String>();

        for (Place thisPlace : place.getPlaces()) {

            // Проверка на игнорирование регистра
            Pattern reg;
            if (ignoreСase) {
                reg = Pattern.compile(regexr, Pattern.CASE_INSENSITIVE|Pattern.UNICODE_CASE);
            } else {
                reg = Pattern.compile(regexr);
            }

            String str = thisPlace.getname();
            Matcher matcher = reg.matcher(str);

            if (matcher.find()) {
                placesGivenTheExpression.add(str);
            }
        }

        return placesGivenTheExpression;
    }

}
