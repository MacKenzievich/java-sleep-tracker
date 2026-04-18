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

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 22, 1),
                LocalDateTime.of(2024, 4, 11, 5, 15),
                "NORMAL"));

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 21, 1),
                LocalDateTime.of(2024, 4, 11, 5, 30),
                "NORMAL"));

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 20, 1),
                LocalDateTime.of(2024, 4, 11, 4, 0),
                "NORMAL"));

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 10, 1),
                LocalDateTime.of(2024, 4, 11, 3, 0),
                "NORMAL"));

        sessions.add(new SleepingSession(
                LocalDateTime.of(2024, 4, 10, 22, 1),
                LocalDateTime.of(2024, 4, 11, 6, 0),
                "NORMAL"));

        SleepAnalysisResult result3 = foundUserType.analyze(sessions);
        assertEquals(UserType.PIGEON, result3.getResult());  //одинаковое количество.

    }

    @Test
    void testSingleNightWithSleep() {
        List<SleepingSession> sessions = List.of(new SleepingSession(
                LocalDateTime.of(2024, 1, 15, 23, 0),
                LocalDateTime.of(2024, 1, 16, 6, 0),
                "NORMAL")
        );
        SleeplessNights analyzer = new SleeplessNights();
        SleepAnalysisResult result = analyzer.analyze(sessions);
        assertEquals(1L, result.getResult());
    }

    @Test
    void testSleepSessionsAcrossMonths() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2024, 1, 28, 23, 0),
                        LocalDateTime.of(2024, 1, 29, 7, 0), "NORMAL"),
                new SleepingSession(LocalDateTime.of(2024, 2, 2, 22, 0),
                        LocalDateTime.of(2024, 2, 3, 6, 30), "NORMAL")
        );
        SleeplessNights analyzer = new SleeplessNights();
        SleepAnalysisResult result = analyzer.analyze(sessions);
        assertEquals(4L, result.getResult());
    }

    @Test
    void testEmptySessionList() {
        List<SleepingSession> sessions = List.of();

        SleeplessNights analyzer = new SleeplessNights();
        SleepAnalysisResult result = analyzer.analyze(sessions);

        assertEquals(0L, result.getResult());
        assertEquals("Количество бессонных ночей", result.getDescription());
    }

    @Test
    void testFirstSessionAfterMidnight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2024, 1, 15, 2, 30),
                        LocalDateTime.of(2024, 1, 15, 8, 0), "NORMAL"),
                new SleepingSession(LocalDateTime.of(2024, 1, 16, 23, 0),
                        LocalDateTime.of(2024, 1, 17, 7, 0), "NORMAL")
        );

        SleeplessNights analyzer = new SleeplessNights();
        SleepAnalysisResult result = analyzer.analyze(sessions);
        assertEquals(0L, result.getResult()); // отсчет идет уже со следующего дня.
    }

    @Test
    void testConsecutiveSleeplessNights() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2024, 1, 10, 20, 0),
                        LocalDateTime.of(2024, 1, 11, 6, 0),"NORMAL"),
                new SleepingSession(LocalDateTime.of(2024, 1, 13, 22, 0),
                        LocalDateTime.of(2024, 1, 14, 7, 0),"NORMAL")
        );

        SleeplessNights analyzer = new SleeplessNights();
        SleepAnalysisResult result = analyzer.analyze(sessions);

        // Бессонные ночи: 11-12 и 12-13 января
        assertEquals(2L, result.getResult());
    }
}



