package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.LongStream;

public class SleeplessNights implements SleepAnalysisFunction {
    private static final LocalTime NIGHT_END_TIME = LocalTime.of(6, 0); // 6 утра

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessionList) {
        if (sessionList == null || sessionList.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0L);
        }

        // Находим первую и последнюю сессию
        LocalDateTime startFirstSession = sessionList.stream()
                .map(SleepingSession::getStartSleep)
                .min(LocalDateTime::compareTo)
                .orElseThrow();

        LocalDateTime endLastSession = sessionList.stream()
                .map(SleepingSession::getEndSleep)
                .max(LocalDateTime::compareTo)
                .orElseThrow();

        // Определяем дату начала и конца анализа
        LocalDate startDate = startFirstSession.toLocalDate();
        if (startFirstSession.toLocalTime().isAfter(LocalTime.NOON)) {
            startDate = startDate.plusDays(1);
        } else {
            startDate = startDate.minusDays(1);
        }

        LocalDate endDate = endLastSession.toLocalDate();

        long totalNights = ChronoUnit.DAYS.between(startDate, endDate);
        LocalDate finalStartDate = startDate;

        long countBessonnyeNochei = LongStream.range(0, totalNights)
                .mapToObj(i -> finalStartDate.plusDays(i))
                .filter(night -> !hasSleepInNight(night, sessionList))
                .count();

        return new SleepAnalysisResult("Количество бессонных ночей", countBessonnyeNochei);
    }

    // Проверяет, есть ли сон в указанную ночь
    private static boolean hasSleepInNight(LocalDate night, List<SleepingSession> sessions) {
        LocalDateTime nightStart = night.atStartOfDay();
        LocalDateTime nightEnd = night.atTime(NIGHT_END_TIME);

        return sessions.stream()
                .anyMatch(session ->
                        session.getStartSleep().isBefore(nightEnd) &&
                                session.getEndSleep().isAfter(nightStart)
                );
    }
}