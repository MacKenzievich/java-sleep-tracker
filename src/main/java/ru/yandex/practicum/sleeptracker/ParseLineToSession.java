package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


public class ParseLineToSession {
    public static List<SleepingSession> parseLineToSession(List<String> stringList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

        List<SleepingSession> sessionsList = stringList.stream()
                .map(line -> {
                    String[] parts = line.split(";");
                    LocalDateTime startSleep = LocalDateTime.parse(parts[0], formatter);
                    LocalDateTime endSleep = LocalDateTime.parse(parts[1], formatter);
                    String sleepQuality = parts[2];

                    return new SleepingSession(startSleep, endSleep, sleepQuality);
                })
                .collect(Collectors.toList());
        return sessionsList;
    }
}

