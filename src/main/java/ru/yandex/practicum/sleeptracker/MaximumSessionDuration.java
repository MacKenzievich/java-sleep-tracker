package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

public class MaximumSessionDuration implements SleepAnalysisFunction {
    @Override
    public  SleepAnalysisResult analyze(List<SleepingSession> sessionList) {
        Duration maxDuration = sessionList.stream()
                .map(s -> Duration.between(s.getStartSleep(), s.getEndSleep()))
                .max(Comparator.naturalOrder())
                .orElse(Duration.ZERO);
        return new SleepAnalysisResult("Максимальная сессия сна (минут)", maxDuration.toMinutes());
    }
}
