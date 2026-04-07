package ru.yandex.practicum.sleeptracker;
public class SleepAnalysisResult {
    String description;
    Object result;

    public SleepAnalysisResult(String description, Object result) {
        this.description = description;
        this.result = result;

    }

    public Object getResult() {
        return result;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description + ": " + result;
    }
}
