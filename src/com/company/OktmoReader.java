package com.company;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class OktmoReader {

    public void readPlaces(String fileName, OktmoData data) {
        int lineCount=0;

        try (
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(fileName)
                                , "cp1251")
                        // читаем файл из двоичного потока
                        // в виде текста с нужной кодировкой
                )
        ) {
            String s;
            while ((s=br.readLine()) !=null ) { // пока readLine() возвращает не null
                lineCount++;
                System.out.println(s);

                String [] q= s.split("[;]");
                System.out.println(Arrays.asList(q));

                System.out.println(s);
                //new Place();

                if (lineCount==20) break; // пример частичного чтения первых 20 строк
            }
        }
        catch (IOException ex) {
            System.out.println("Reading error in line "+lineCount);
            ex.printStackTrace();
        }
    }
}
