package com.example.amadeus.app.entity;

/**
 * A class presenting an age range and an average age
 *
 * @author TheNhan
 */
public class AgeRange {

    private String range;
    private float average;

    public AgeRange() {
    }

    public AgeRange(String range, float average) {
        this.range = range;
        this.average = average;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }
}
