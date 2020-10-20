package com.kamil.model;

public class Point {

    private double lat;
    private double lon;
    private String latestTotalCases;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(String latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    @Override
    public String toString() {
        return "Point{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", latestTotalCases='" + latestTotalCases + '\'' +
                '}';
    }
}
