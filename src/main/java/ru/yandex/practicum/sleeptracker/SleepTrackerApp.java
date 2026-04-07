package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SleepTrackerApp {

    public static void main(String[] args) {

        List<String> stringList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream
                ("src/main/resources/sleep_log.txt"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringList.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");

        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом");

        }

        List<SleepingSession> sleepingSessionList = ParseLineToSession.parseLineToSession(stringList);

        List<SleepAnalysisFunction> functionsList = new ArrayList<>();
        functionsList.add(new NumberOfSleepSessions());
        functionsList.add(new MinimumSessionDuration());
        functionsList.add(new MaximumSessionDuration());
        functionsList.add(new AverageSessionDuration());
        functionsList.add(new NumberOfSessionsPoorSleepQuality());
        functionsList.add(new SleeplessNights());
        functionsList.add(new FoundUserType());

        if (!sleepingSessionList.isEmpty()) {
            functionsList.stream()
                    .forEach(f -> System.out.println(f.analyze(sleepingSessionList)));
        } else {
            System.out.println("Список сессий пуст!");
        }

    }
}