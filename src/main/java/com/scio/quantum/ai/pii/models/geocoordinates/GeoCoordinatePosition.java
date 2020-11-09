package com.scio.quantum.ai.pii.models.geocoordinates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoCoordinatePosition {

    @SerializedName("Start")
    @Expose
    private int start;
    @SerializedName("Stop")
    @Expose
    private int stop;

    public GeoCoordinatePosition(int start, int stop) {
        this.start = start;
        this.stop = stop;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    @Override
    public String toString() {
        return "GeoCoordinatePosition{" +
                "start=" + start +
                ", stop=" + stop +
                '}';
    }
}
