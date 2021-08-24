package com.company;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OktmoReader {

    public void readPlaces(String fileName, OktmoData data) {
        int lineCount=0;

        try ( BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fileName)
                        , "cp1251")
        )
        ) {
            String str;
            StringBuilder codeStr = new StringBuilder("");
            while ((str=br.readLine()) !=null ) { // пока readLine() возвращает не null
                lineCount++;

                // Проверка кода на наличие нулевых значение

                String [] q= str.split(";");
                codeStr.setLength(0);

                if (q[3].replace("\"", "").equals("000")) {
                    continue;
                }

                for (int i = 0; i < 4; i++) {
                    String symbol = q[i].replace("\"", "");
                    codeStr.append(symbol);
                }

                // Парсинг сток и запись в список

                Long code = 0L;
                String status = "";
                String name = "";
                try {
                    // Преобразование кода в Long
                    code = Long.parseLong(codeStr.toString());

                    // Проверка на наличие статуса
                    if (Character.isUpperCase(q[6].charAt(1))) {
                        status = "Нет статуса";
                        name = q[6].substring(1, q[6].length() - 1);
                    } else {
                        int indexSplit = q[6].indexOf(" ");

                        status = indexSplit != -1 ? q[6].substring(1, indexSplit) : "Нет статуса";
                        name = indexSplit != -1 ? q[6].substring(indexSplit + 1, q[6].length() - 1)  : q[6].substring(1, q[6].length() - 1);
                    }

                    Place newPlace = new Place(code, status, name);
                    data.addPlace(newPlace, status);

                    // Добавление статуса в карту статусов
                    OktmoAnalyzer.fillingMapStatuses(status);

                } catch (Exception e) {
                    e.printStackTrace();

                    System.out.println("Ошибка в сроке файла №" + lineCount);
                    System.out.println("code = " + code);
                    System.out.println("name = " + name);
                    System.out.println("status = " + status);
                }

//                if (lineCount == 50) break; // для проверки сортировки
            }
        }
        catch (IOException ex) {
            System.out.println("Reading error in line "+lineCount);
            ex.printStackTrace();
        }
    }

    public void readPlacesReg(String fileName, OktmoData data) {
        int lineCount=0;

        try ( BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fileName)
                        , "cp1251")
        )
        ) {
            String str;
            StringBuilder codeStr = new StringBuilder("");
            Pattern reg = Pattern.compile(OktmoMain.PLACE_REG);

            while ((str=br.readLine()) !=null ) { // пока readLine() возвращает не null
                lineCount++;

                Matcher matcher = reg.matcher(str);

                Long code = 0L;
                String status = "";
                String name = "";
                codeStr.setLength(0);

                if (matcher.find()) {
                    codeStr = new StringBuilder("");
                    codeStr.append(matcher.group(1));
                    codeStr.append(matcher.group(2));
                    codeStr.append(matcher.group(3));
                    codeStr.append(matcher.group(4));

                    code = Long.parseLong(codeStr.toString());
                    status = matcher.group(6) != null ? matcher.group(6) : "Нет статуса";
                    name = matcher.group(7);

                    Place newPlace = new Place(code, status, name);
                    data.addPlace(newPlace, status);

                    // Добавление статуса в карту статусов
                    OktmoAnalyzer.fillingMapStatuses(status);
                }

//                if (lineCount == 50) break; // для проверки сортировки
            }
        }
        catch (IOException ex) {
            System.out.println("Reading error in line "+lineCount);
            ex.printStackTrace();
        }
    }

    public void readPlacesGroup(String fileName, OktmoData data) {
        int lineCount=0;

        try ( BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fileName)
                        , "cp1251")
        )
        ) {
            OKTMOGroup oktmoGroupRegion = null;
            OKTMOGroup oktmoGroupDistrictOrCity = null;
            OKTMOGroup oktmoGroupVillageCouncil = null;

            Pattern reg = Pattern.compile(OktmoMain.GROUP_REG);
            Pattern regPlace = Pattern.compile(OktmoMain.PLACE_REG);
            Matcher matcher;

            String str;
            StringBuilder codeStr = new StringBuilder("");

            Long codeUpLevel = 0L;

            while ((str = br.readLine()) !=null ) { // пока readLine() возвращает не null
                lineCount++;

                matcher = reg.matcher(str);
                Long code = 0L;
                String name = "";
                String status = "";
                OktmoLevel level;
                codeStr.setLength(0);

                if (matcher.find()) {

                    if (matcher.group(2).equals("000") && matcher.group(3).equals("000") &&
                            matcher.group(4).equals("000") && matcher.group(5).startsWith("Населенные пункты")) {
                        level = OktmoLevel.REGION;

                        codeStr.append(matcher.group(1));
                        codeStr.append(matcher.group(2));
                        codeStr.append(matcher.group(3));
                        codeStr.append(matcher.group(4));

                        code = Long.parseLong(codeStr.toString());
                        name = matcher.group(5);

                        oktmoGroupRegion = new OKTMOGroup(level, name, code);
                        data.getOktmoGroupMap().put(code, new TreeMap<Long, OKTMOGroup>());

                        // Вложенная группа списка
                        data.addOktmoGroupList(oktmoGroupRegion);

                        codeUpLevel = code;

                        continue;
                    }

                    if (!matcher.group(2).equals("000") && matcher.group(3).equals("000") &&
                            matcher.group(4).equals("000") && !matcher.group(5).startsWith("Муниципальные районы")) {
                        level = OktmoLevel.DISTRICT_OR_CITY;

                        codeStr.append(matcher.group(1));
                        codeStr.append(matcher.group(2));
                        codeStr.append(matcher.group(3));
                        codeStr.append(matcher.group(4));

                        code = Long.parseLong(codeStr.toString());
                        name = matcher.group(5);

                        oktmoGroupDistrictOrCity = new OKTMOGroup(level, name, code);
                        data.getOktmoGroupMap().get(codeUpLevel).put(code, new TreeMap<Long, <OKTMOGroup>>());

                        // Вложенная группа списка
                        oktmoGroupRegion.addOktmoGroupInnerList(oktmoGroupDistrictOrCity);

                        codeUpLevel = code;

                        continue;
                    }

                    if (!matcher.group(2).equals("000") && !matcher.group(3).equals("000") &&
                            matcher.group(4).equals("000")  && matcher.group(5).startsWith("Населенные пункты")) {
                        level = OktmoLevel.VILLAGE_COUNCIL;

                        codeStr.append(matcher.group(1));
                        codeStr.append(matcher.group(2));
                        codeStr.append(matcher.group(3));
                        codeStr.append(matcher.group(4));

                        code = Long.parseLong(codeStr.toString());
                        name = matcher.group(5);

                        oktmoGroupVillageCouncil = new OKTMOGroup(level, name, code);
                        oktmoGroupVillageCouncil.addOktmoGroupInnerMap(code, new TreeMap<Long, OKTMOGroup>());

                        // Вложенная группа списка
                        oktmoGroupDistrictOrCity.addOktmoGroupInnerList(oktmoGroupVillageCouncil);

                        continue;
                    }
                }

                matcher = regPlace.matcher(str);
//
                if (matcher.find()) {
                    level = OktmoLevel.PLACE;

                    codeStr = new StringBuilder("");
                    codeStr.append(matcher.group(1));
                    codeStr.append(matcher.group(2));
                    codeStr.append(matcher.group(3));
                    codeStr.append(matcher.group(4));

                    code = Long.parseLong(codeStr.toString());
                    status = matcher.group(6) != null ? matcher.group(6) : "Нет статуса";
                    name = matcher.group(7);

                    Place newPlace = new Place(code, status, name);
                    oktmoGroupVillageCouncil.addPlace(newPlace);
                }

                if (lineCount == 50) break; // для проверки сортировки
            }
        }
        catch (IOException ex) {
            System.out.println("Reading error in line "+lineCount);
            ex.printStackTrace();
        }
    }
}