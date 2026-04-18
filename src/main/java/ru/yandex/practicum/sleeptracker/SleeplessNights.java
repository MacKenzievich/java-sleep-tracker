package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.LongStream;

public class SleeplessNights implements SleepAnalysisFunction {
    private static final LocalTime NIGHT_END_TIME = LocalTime.of(6, 0); // 6 утра

    public SleepAnalysisResult analyze(List<SleepingSession> sessionList) {
        if (sessionList == null || sessionList.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0L);
        }

        LocalDateTime startFirstSession = sessionList.stream()
                .map(SleepingSession::getStartSleep)
                .min(LocalDateTime::compareTo)
                .orElseThrow();
        LocalDateTime endLastSession = sessionList.stream()
                .map(SleepingSession::getEndSleep)
                .max(LocalDateTime::compareTo)
                .orElseThrow();

        LocalDate firstNightDate = startFirstSession.toLocalDate();
        if (startFirstSession.toLocalTime().isAfter(NIGHT_END_TIME)) {
            firstNightDate = firstNightDate.minusDays(1);
        }

        LocalDate lastNightDate = endLastSession.toLocalDate();
        if (endLastSession.toLocalTime().isBefore(NIGHT_END_TIME)) {
            lastNightDate = lastNightDate.minusDays(1);
        }

        if (firstNightDate.isAfter(lastNightDate)) {
            boolean hasSleep = hasSleepInNight(firstNightDate, sessionList);
            return new SleepAnalysisResult("Количество бессонных ночей", hasSleep ? 0L : 1L);
        }

        long totalNights = ChronoUnit.DAYS.between(firstNightDate, lastNightDate) + 1;

        long countBessonnyeNochei = LongStream.range(0, totalNights)
                .mapToObj(firstNightDate::plusDays)
                .filter(night -> !hasSleepInNight(night, sessionList))
                .count();

        return new SleepAnalysisResult("Количество бессонных ночей", countBessonnyeNochei);
    }

    private static boolean hasSleepInNight(LocalDate night, List<SleepingSession> sessions) {
        LocalDateTime nightStart = night.atStartOfDay(); // 00:00
        LocalDateTime nightEnd = night.plusDays(1).atTime(NIGHT_END_TIME); // 06:00 следующего дня

        return sessions.stream()
                .anyMatch(session -> {
                    LocalDateTime sessionStart = session.getStartSleep();
                    LocalDateTime sessionEnd = session.getEndSleep();

                    return sessionStart.isBefore(nightEnd) &&
                            sessionEnd.isAfter(nightStart);
                });
    }
}