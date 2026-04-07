package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;

public class FoundUserType implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessionList) {
        final int[] owl = {0};
        final int[] lark = {0};
        final int[] pigeon = {0};
        UserType userType;
        sessionList.stream()
                .filter(s -> s.getStartSleep().toLocalTime().isAfter(LocalTime.of(9, 0))
                        && s.getEndSleep().toLocalTime().isBefore(LocalTime.of(22, 0)))
                .forEach(s -> {
                    LocalTime sleepTime = s.getStartSleep().toLocalTime();
                    LocalTime wakeTime = s.getEndSleep().toLocalTime();

                    if (sleepTime.isAfter(LocalTime.of(23, 0)) && wakeTime.isAfter(LocalTime.of(9, 0))) {
                        owl[0] = owl[0] + 1; // сова
                    } else if (sleepTime.isBefore(LocalTime.of(22, 0)) && wakeTime.isBefore(LocalTime.of(7, 0))) {
                        lark[0] = lark[0] + 1; // жаворонок
                    } else {
                        pigeon[0]++; // голубь
                    }
                });
        if (owl[0] > lark[0] && owl[0] > pigeon[0]){
            userType = UserType.OWL;
        } else if (lark[0] > owl[0] && lark[0] > pigeon[0]) {
            userType = UserType.LARK;
        } else if (pigeon[0] > owl[0] && pigeon[0] > lark[0]){
            userType = UserType.PIGEON;
        } else {
            userType = UserType.PIGEON;
        }

        return new SleepAnalysisResult("Тип пользователя", userType);
    }
}
