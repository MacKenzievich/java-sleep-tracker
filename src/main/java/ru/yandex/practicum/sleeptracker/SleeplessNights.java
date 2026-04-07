package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.LongStream;

public class SleeplessNights implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessionList) {

        LocalDateTime startFirstSession = sessionList.stream()
                .map(SleepingSession::getStartSleep)
                .min(LocalDateTime::compareTo)
                .get();
        LocalDateTime endLastSession = sessionList.stream()
                .map(SleepingSession::getEndSleep)
                .max(LocalDateTime::compareTo)
                .get();


        LocalDate startDate = startFirstSession.toLocalDate();
        if (startFirstSession.toLocalTime().isAfter(LocalTime.NOON)) {
            startDate = startDate.plusDays(1);
        } else {
            startDate = startDate.minusDays(1);
        }

        LocalDate endDate = endLastSession.toLocalDate();

        long totalNights = ChronoUnit.DAYS.between(startDate, endDate);
        LocalDate finalStartDate = startDate;
        long countNight = LongStream.range(0, totalNights)
                .mapToObj(i -> finalStartDate.plusDays(i))
                .filter(night -> isSleeplessNight(night, sessionList))
                .count();
        return new SleepAnalysisResult("Количество бессоных ночей", countNight);
    }

    private static boolean isSleeplessNight(LocalDate night, List<SleepingSession> sessions) {
        LocalDateTime nightStart = night.atStartOfDay();
        LocalDateTime nightEnd = night.atTime(6, 0);

        return sessions.stream()
                .noneMatch(session -> session.getStartSleep().isBefore(nightEnd) && session.getEndSleep().isAfter(nightStart));
    }
}
