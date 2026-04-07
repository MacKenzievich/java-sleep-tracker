package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {

    private static List<SleepingSession> sessions;
    private static MaximumSessionDuration maximumSessionDuration;
    private static MinimumSessionDuration minimumSessionDuration;
    private static AverageSessionDuration averageSessionDurationl;
    private static FoundUserType foundUserType;
    private static NumberOfSessionsPoorSleepQuality numberOfSessionsPoorSleepQuality;
    private static NumberOfSleepSessions numberOfSleepSessions;
    private static SleeplessNights sleeplessNights;

    @BeforeEach
    void setup() {
        sessions = new ArrayList<>();
        sessions.add(new SleepingSession(LocalDateTime.of(2024, 4, 10, 22, 0),
                LocalDateTime.of(2024, 4, 11, 0, 0),
                "BAD"
        )); // 2 часа

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 11, 23, 0),
                LocalDateTime.of(2024, 4, 12, 0, 0),
                "GOOD"
        )); // 1 час

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 21, 0),
                LocalDateTime.of(2024, 4, 10, 22, 30),
                "GOOD"
        )); // 1.5 часа

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 20, 0),
                LocalDateTime.of(2024, 4, 10, 20, 30),
                "NORMAL"
        )); // 0 минут

        maximumSessionDuration = new MaximumSessionDuration();
        minimumSessionDuration = new MinimumSessionDuration();
        averageSessionDurationl = new AverageSessionDuration();
        foundUserType = new FoundUserType();
        numberOfSessionsPoorSleepQuality = new NumberOfSessionsPoorSleepQuality();
        numberOfSleepSessions = new NumberOfSleepSessions();
        sleeplessNights = new SleeplessNights();
    }

    @Test
    void testMaximumSleepDuration() {
        SleepAnalysisResult result = maximumSessionDuration.analyze(sessions);
        assertEquals(120L, result.getResult());

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 20, 0),
                LocalDateTime.of(2024, 4, 11, 4, 0),
                "NORMAL"));

        SleepAnalysisResult result2 = maximumSessionDuration.analyze(sessions);
        assertEquals(480L, result2.getResult());
    }


    @Test
    void testMinimumSleepDuration() {
        SleepAnalysisResult result = minimumSessionDuration.analyze(sessions);
        assertEquals(30L, result.getResult());

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 20, 0),
                LocalDateTime.of(2024, 4, 10, 20, 0),
                "NORMAL")); // 0 минут

        SleepAnalysisResult result2 = minimumSessionDuration.analyze(sessions);
        assertEquals(0L, result2.getResult());
    }

    @Test
    void testAverageSessionDuration() {
        SleepAnalysisResult result = averageSessionDurationl.analyze(sessions);
        assertEquals(75L, result.getResult());

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 20, 0),
                LocalDateTime.of(2024, 4, 10, 20, 0),
                "NORMAL")); // 0 минут

        SleepAnalysisResult result2 = averageSessionDurationl.analyze(sessions);
        assertEquals(60L, result2.getResult());
    }

    @Test
    void testNumberOfSleepSessions() {
        SleepAnalysisResult result = numberOfSleepSessions.analyze(sessions);
        assertEquals(4, result.getResult());

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 20, 0),
                LocalDateTime.of(2024, 4, 10, 20, 0),
                "NORMAL")); // 0 минут

        SleepAnalysisResult result2 = numberOfSleepSessions.analyze(sessions);
        assertEquals(5, result2.getResult());
    }

    @Test
    void testNumberOfSessionsPoorSleepQuality() {
        SleepAnalysisResult result = numberOfSessionsPoorSleepQuality.analyze(sessions);
        assertEquals(1L, result.getResult());

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 20, 0),
                LocalDateTime.of(2024, 4, 10, 20, 0),
                "BAD")); // 0 минут

        SleepAnalysisResult result2 = numberOfSessionsPoorSleepQuality.analyze(sessions);
        assertEquals(2L, result2.getResult());
    }

    @Test
    void testFoundUserType() {
        SleepAnalysisResult result = foundUserType.analyze(sessions);
        assertEquals(UserType.PIGEON, result.getResult());

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 23, 1),
                LocalDateTime.of(2024, 4, 11, 9, 15),
                "NORMAL"));

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 23, 1),
                LocalDateTime.of(2024, 4, 11, 9, 30),
                "NORMAL"));

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 23, 1),
                LocalDateTime.of(2024, 4, 11, 10, 0),
                "NORMAL"));

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 23, 1),
                LocalDateTime.of(2024, 4, 11, 11, 0),
                "NORMAL"));

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 23, 1),
                LocalDateTime.of(2024, 4, 11, 12, 0),
                "NORMAL"));


        SleepAnalysisResult result2 = foundUserType.analyze(sessions);
        assertEquals(UserType.OWL, result2.getResult());
    }


    @Test
    void testSleeplessNights() {
        List<SleepingSession> nightSessions = new ArrayList<>();
        // с 22:00 до 23:00
        nightSessions.add(new SleepingSession(LocalDateTime.of(2024, 4, 10, 22, 0),
                LocalDateTime.of(2024, 4, 10, 23, 0),
                "NORMAL"));
        //с 23:30 до 00:30 следующего дня
        nightSessions.add(new SleepingSession(LocalDateTime.of(2024, 4, 11, 23, 30),
                LocalDateTime.of(2024, 4, 12, 0, 30),
                "NORMAL"));

        SleepAnalysisResult result = sleeplessNights.analyze(nightSessions);
        assertEquals(1L, result.getResult());

        nightSessions.add(new SleepingSession(LocalDateTime.of(2024, 4, 9, 20, 0),
                LocalDateTime.of(2024, 4, 10, 0, 0),
                "NORMAL"));

        SleepAnalysisResult result2 = sleeplessNights.analyze(nightSessions);
        assertEquals(2L, result2.getResult());

        nightSessions.add(new SleepingSession(LocalDateTime.of(2024, 4, 9, 14, 0),
                LocalDateTime.of(2024, 4, 9, 20, 0),
                "NORMAL"));

        SleepAnalysisResult result3 = sleeplessNights.analyze(nightSessions);
        assertEquals(2L, result3.getResult());

        nightSessions.add(new SleepingSession(LocalDateTime.of(2024, 4, 14, 14, 0),
                LocalDateTime.of(2024, 4, 15, 0, 30),
                "NORMAL"));

        SleepAnalysisResult result4 = sleeplessNights.analyze(nightSessions);
        assertEquals(4L, result4.getResult());
    }


}