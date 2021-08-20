package com.company;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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
                for (int i = 0; i < 4; i++) {
                    String symbol = q[i].replace("\"", "");
                    if (Integer.parseInt(symbol) == 0) {
                        codeStr.setLength(0);
                        break;
                    } else {
                        codeStr.append(symbol);
                    }
                }

                // Парсинг сток и запись в список

                if (codeStr.length() > 0) {
                    Long code = 0L;
                    String status = "";
                    String name = "";
                    try {
                        // Преобразование кода в Long
                        code = Long.parseLong(codeStr.toString());

                        int indexSplit = q[6].indexOf(" ");
                        // || !q[6].startsWith("[А-Я]")
                        status = indexSplit != -1 ? q[6].substring(1, indexSplit) : "Статус неопределен";
                        name = indexSplit != -1 ? q[6].substring(indexSplit + 1, q[6].length() - 1)  : "Статус неопределен";

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
                }

//                if (lineCount==20000) break; // для проверки сортировки
            }
        }
        catch (IOException ex) {
            System.out.println("Reading error in line "+lineCount);
            ex.printStackTrace();
        }
    }
}
