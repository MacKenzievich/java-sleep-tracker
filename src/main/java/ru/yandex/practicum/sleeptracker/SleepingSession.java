package ru.yandex.practicum.sleeptracker;


import java.time.LocalDateTime;


public class SleepingSession {
    private LocalDateTime startSleep;
    private LocalDateTime endSleep;
    private String sleepQuality;

    public SleepingSession(LocalDateTime startSleep, LocalDateTime endSleep, String sleepQuality) {
        this.startSleep = startSleep;
        this.endSleep = endSleep;
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getStartSleep() {
        return startSleep;
    }

    public LocalDateTime getEndSleep() {
        return endSleep;
    }

    public String getSleepQuality() {
        return sleepQuality;
    }

}
