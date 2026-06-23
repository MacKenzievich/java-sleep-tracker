package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

public class MinimumSessionDuration implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessionList) {
        Duration minDuration = sessionList.stream()
                .map(s -> Duration.between(s.getStartSleep(), s.getEndSleep()))
                .min(Comparator.naturalOrder())
                .orElse(Duration.ZERO);
        return new SleepAnalysisResult("Минимальная сессеия сна (минут)", minDuration.toMinutes());

    }

}
