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

                    int indexSplit = q[6].indexOf(" ");

                    // Данный блок кода добавлен из-за одного населенного пункта:
                    // "94";"604";"420";"171";"8";"2";"Балезино-3";;;"000";"0";14.06.2013;01.01.2014
                    // у него не прописан статус НП
                    if (indexSplit == -1) {
                        continue;
                    }

                    status = indexSplit != -1 ? q[6].substring(1, indexSplit) : "Нет статуса";
                    name = indexSplit != -1 ? q[6].substring(indexSplit + 1, q[6].length() - 1)  : q[6].substring(1, q[6].length() - 1);

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
            while ((s=br.readLine()) !=null ) { // пока readLine() возвращает не null
                lineCount++;

                Pattern reg = Pattern.compile(OktmoMain.REG);
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
                    status = matcher.group(5);
                    name = matcher.group(6);

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
}
