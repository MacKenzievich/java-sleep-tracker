package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class NumberOfSessionsPoorSleepQuality implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessionList) {
        Long count = sessionList.stream()
                .filter( s -> s.getSleepQuality().equals("BAD"))
                .count();
        return new SleepAnalysisResult("Количество сессий с плохим уровнем сна", count);
    }
}
