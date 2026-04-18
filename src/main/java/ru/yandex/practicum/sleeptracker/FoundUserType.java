package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FoundUserType implements SleepAnalysisFunction {
    private static final LocalTime NIGHT_START_TIME = LocalTime.of(22, 0);
    private static final LocalTime NIGHT_END_TIME = LocalTime.of(9, 0);
    private static final LocalTime SLEEP_START_BOUNDARY = LocalTime.of(23, 0);
    private static final LocalTime SLEEP_WAKE_BOUNDARY = LocalTime.of(7, 0);

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessionList) {
        AtomicInteger owlCount = new AtomicInteger();
        AtomicInteger larkCount = new AtomicInteger(0);
        AtomicInteger pigeonCount = new AtomicInteger(0);
        UserType userType;

        sessionList.stream()
                .filter(s -> s.getStartSleep().toLocalTime().isAfter(NIGHT_END_TIME)
                        && s.getEndSleep().toLocalTime().isBefore(NIGHT_START_TIME))
                .forEach(s -> {
                    LocalTime sleepTime = s.getStartSleep().toLocalTime();
                    LocalTime wakeTime = s.getEndSleep().toLocalTime();

                    if (sleepTime.isAfter(SLEEP_START_BOUNDARY) && wakeTime.isAfter(NIGHT_END_TIME)) {
                        owlCount.getAndIncrement(); // сова
                    } else if (sleepTime.isBefore(NIGHT_START_TIME) && wakeTime.isBefore(SLEEP_WAKE_BOUNDARY)) {
                        larkCount.getAndIncrement(); // жаворонок
                    } else {
                        pigeonCount.getAndIncrement(); // голубь
                    }
                });

        if (owlCount.get() > larkCount.get() && owlCount.get() > pigeonCount.get()) {
            userType = UserType.OWL;
        } else if (larkCount.get() > owlCount.get() && larkCount.get() > pigeonCount.get()) {
            userType = UserType.LARK;
        } else if (pigeonCount.get() > owlCount.get() && pigeonCount.get() > larkCount.get()) {
            userType = UserType.PIGEON;
        } else {
            userType = UserType.PIGEON;
        }

        return new SleepAnalysisResult("Тип пользователя", userType);
    }
}