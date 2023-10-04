package com.example.demo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputUtils {
    public static Map<String,LocalDateTime> analiz(String nameFile){
        Map<String,LocalDateTime> means = new HashMap<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(new File(nameFile)));
            String line = "";
            while ((line = reader.readLine()) != null) {
                // token identifier is a space
                String[] data = line.trim().split("\\s+");
                    if (data.length >= 2) {
                        if (data[0].equals("SPLC1")) {
                            Double interval=Double.parseDouble(data[3].replace(",", ".").replace("<", ""));
                            DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                            LocalDate ldt = LocalDate.parse(data[1], dTF);
                            means.put(String.format("%.3f", interval),ldt.atStartOfDay());

                            }
                        }
                    }

            reader.close();
//            Stream<Map.Entry<String,LocalDateTime>> sorted =
//                    means.entrySet().stream()
//                            .sorted(Map.Entry.comparingByValue());
//            means = sorted.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
//            System.out.println(means);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return means;

    }

    public static Map<String,LocalDateTime> analizHIVAg(String nameFile){
        Map<String,LocalDateTime> means = new HashMap<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(new File(nameFile)));
            String line = "";
            while ((line = reader.readLine()) != null) {
                // token identifier is a space
                String[] data = line.trim().split("\\s+");
                if (data.length >= 2) {
                    if (data[0].equals("SPLC2")) {
                        Double interval=Double.parseDouble(data[3].replace(",", ".").replace("<", ""));
                        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        LocalDate ldt = LocalDate.parse(data[1], dTF);
                        means.put(String.format("%.3f", interval),ldt.atStartOfDay());
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return means;

    }
}
