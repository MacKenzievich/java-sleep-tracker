package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class NumberOfSleepSessions implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessionList) {
        return new SleepAnalysisResult("Количество ссесий сна", sessionList.size());
    }
}
