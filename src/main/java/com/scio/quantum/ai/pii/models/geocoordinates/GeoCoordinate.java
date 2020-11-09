package com.scio.quantum.ai.pii.models.geocoordinates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoCoordinate {

    @SerializedName("Coordinates")
    @Expose
    private String coordinates;
    @SerializedName("Position")
    @Expose
    private GeoCoordinatePosition position;

    public GeoCoordinate(String coordinates, GeoCoordinatePosition position) {
        this.coordinates = coordinates;
        this.position = position;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public GeoCoordinatePosition getPosition() {
        return position;
    }

    public void setPosition(GeoCoordinatePosition position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "GeoCoordinate{" +
                "coordinates='" + coordinates + '\'' +
                ", position=" + position +
                '}';
    }
}
