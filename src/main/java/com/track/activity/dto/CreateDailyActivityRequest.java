package com.track.activity.dto;

public class CreateDailyActivityRequest {

    private int actualMinutes;
    private String notes;

    public CreateDailyActivityRequest() {
    }

    public int getActualMinutes() {
        return actualMinutes;
    }

    public void setActualMinutes(int actualMinutes) {
        this.actualMinutes = actualMinutes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}