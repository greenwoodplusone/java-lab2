package com.company;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
            String s;
            while ((s=br.readLine()) !=null ) { // пока readLine() возвращает не null
                lineCount++;

                // Проверка кода на наличие нулевых значение

                String [] q= s.split(";");
                StringBuilder codeStr = new StringBuilder("");

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
            String s;
            Pattern reg = Pattern.compile(OktmoMain.REG_PLACE);

            while ((s=br.readLine()) !=null ) { // пока readLine() возвращает не null
                lineCount++;


                Matcher matcher = reg.matcher(s);

                Long code = 0L;
                String status = "";
                String name = "";
                StringBuilder codeStr;
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

            String s;
            while ((s = br.readLine()) !=null ) { // пока readLine() возвращает не null
                lineCount++;

                Pattern regRegion = Pattern.compile(OktmoMain.REGION_REG);
                Pattern regDistrictOrCity = Pattern.compile(OktmoMain.DISTRICT_OR_CITY);
                Pattern regVillageCouncil = Pattern.compile(OktmoMain.VILLAGE_COUNCIL);
                Pattern regRegPlace = Pattern.compile(OktmoMain.REG_PLACE);

                Matcher matcher;
                Long code = 0L;
                String name = "";
                String status = "";
                OktmoLevel level;

                if (regRegion.matcher(s).find()) {
                    level = OktmoLevel.REGION;

                    matcher = regRegion.matcher(s);

                    StringBuilder codeStr;

                    if (matcher.find()) {
                        codeStr = new StringBuilder("");
                        codeStr.append(matcher.group(1));
                        codeStr.append(matcher.group(2));
                        codeStr.append(matcher.group(3));
                        codeStr.append(matcher.group(4));

                        code = Long.parseLong(codeStr.toString());
                        name = matcher.group(5);

                        oktmoGroupRegion = new OKTMOGroup(level, name, code);
                        data.addOktmoGroupList(oktmoGroupRegion);
                    }

                    continue;
                }

                if (regDistrictOrCity.matcher(s).find()) {
                    level = OktmoLevel.DISTRICT_OR_CITY;

                    matcher = regDistrictOrCity.matcher(s);

                    StringBuilder codeStr;

                    if (matcher.find()) {
                        codeStr = new StringBuilder("");
                        codeStr.append(matcher.group(1));
                        codeStr.append(matcher.group(2));
                        codeStr.append(matcher.group(3));
                        codeStr.append(matcher.group(4));

                        code = Long.parseLong(codeStr.toString());
                        name = matcher.group(7);

                        oktmoGroupDistrictOrCity = new OKTMOGroup(level, name, code);
                        oktmoGroupRegion.addOKTMOGroup(oktmoGroupDistrictOrCity);
                    }

                    continue;
                }

                if (regVillageCouncil.matcher(s).find()) {
                    level = OktmoLevel.VILLAGE_COUNCIL;

                    matcher = regVillageCouncil.matcher(s);

                    StringBuilder codeStr;

                    if (matcher.find()) {
                        codeStr = new StringBuilder("");
                        codeStr.append(matcher.group(1));
                        codeStr.append(matcher.group(2));
                        codeStr.append(matcher.group(3));
                        codeStr.append(matcher.group(4));

                        code = Long.parseLong(codeStr.toString());
                        name = matcher.group(5);

                        oktmoGroupVillageCouncil = new OKTMOGroup(level, name, code);
                        oktmoGroupDistrictOrCity.addOKTMOGroup(oktmoGroupVillageCouncil);
                    }

                    continue;
                }

                if (regRegPlace.matcher(s).find()) {
                    level = OktmoLevel.PLACE;

                    matcher = regRegPlace.matcher(s);

                    StringBuilder codeStr;

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
                        oktmoGroupVillageCouncil.addPlace(newPlace);

                        // Добавление статуса в карту статусов
                        OktmoAnalyzer.fillingMapStatuses(status);
                    }
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