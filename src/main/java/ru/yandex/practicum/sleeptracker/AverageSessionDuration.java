package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class AverageSessionDuration implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessionList) {
        Duration totalDuration = sessionList.stream()
                .map(s -> Duration.between(s.getStartSleep(), s.getEndSleep()))
                .reduce(Duration.ZERO, Duration::plus);

        Duration averageDuration = totalDuration.dividedBy(sessionList.size());

        return new SleepAnalysisResult("Средняя продолжительность сессии (минут)", averageDuration.toMinutes());
    }
}
