package com.kamil.service;

import com.kamil.model.LocationStats;
import com.kamil.model.Point;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class CovidService {

    private static final String url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";



    private Iterable<CSVRecord> getCsvRecords() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());
        Reader csvReader = new StringReader(send.body());
        return CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvReader);
    }

    public List<LocationStats> virusStats() throws IOException, InterruptedException {
        List<LocationStats> locationStats = new ArrayList<>();
        Iterable<CSVRecord> records = getCsvRecords();
        for (CSVRecord record : records) {
            LocationStats stat = new LocationStats();
            String state = record.get("Province/State");
            stat.setState(state);
            String country = record.get("Country/Region");
            stat.setCountry(country);
            int totalCases = Integer.parseInt(record.get(record.size()-1));
            stat.setLatestTotalCases(totalCases);
            int beforeDayTotalCases = Integer.parseInt(record.get(record.size()-2));
            stat.setDayBeforeTotalCases(totalCases-beforeDayTotalCases);
            locationStats.add(stat);
        }
         return locationStats;
    }

    public List<Point> virusPoint() throws IOException, InterruptedException {
        List<Point> pointMap = new ArrayList<>();
        Iterable<CSVRecord> points = getCsvRecords();
        for(CSVRecord record : points){
            Point point = new Point();
            Double lat = Double.parseDouble(record.get("Lat"));
            point.setLat(lat);
            Double lon = Double.parseDouble(record.get("Long"));
            point.setLon(lon);
            String totalCases = record.get(record.size()-1);
            point.setLatestTotalCases(totalCases);
            pointMap.add(point);
        }

        return pointMap;
    }

}
