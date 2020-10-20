package com.kamil;


import com.kamil.model.LocationStats;
import com.kamil.model.Point;
import com.kamil.service.CovidService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static org.hamcrest.core.Is.is;


public class CSVUtilsTest {

    private static  String url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private Iterable<CSVRecord> getCsvRecords() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());
        Reader csvReader = new StringReader(send.body());
        return CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvReader);
    }

    private List<LocationStats> saveToList() throws IOException, InterruptedException {
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

            locationStats.add(stat);
        }
        return locationStats;
    }


    private List<Point> virusPoint() throws IOException, InterruptedException {
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

    @Test
        public void test_no_state() throws IOException, InterruptedException {
            Iterable<CSVRecord> records = getCsvRecords();
            for (CSVRecord result: records) {
                String state = result.get("Province/State");
                assertThat(state, is(""));
                String country = result.get("Country/Region");
                assertThat(country, is("Afghanistan"));
                break;
            }
    }
    @Test
    public void test_no_state2() throws IOException, InterruptedException {
        List<LocationStats> locationStats = saveToList();

        String state = locationStats.get(8).getState();
        assertThat(state, is("Australian Capital Territory"));
        String country = locationStats.get(8).getCountry();
        assertThat(country,is("Australia"));

        String state2 = locationStats.get(167).getState();
        assertThat(state2, is("Curacao"));
        String country2 = locationStats.get(167).getCountry();
        assertThat(country2,is("Netherlands"));
    }

    @Test
    public void test_no_point() throws IOException, InterruptedException {
        Iterable<CSVRecord> points = getCsvRecords();
        for(CSVRecord record : points){
            Double lat = Double.parseDouble(record.get("Lat"));
            assertThat(lat,is(33.0));
            Double lon = Double.parseDouble(record.get("Long"));
            assertThat(lon,is(65.0));
            int totalCases = Integer.parseInt(record.get("6/2/20"));
            assertThat(totalCases,is(16509));
            break;
        }

    }

    @Test
    public void test_no_point2() throws IOException, InterruptedException {
        List<Point> points = virusPoint();

        Double lat = points.get(51).getLat();
        assertThat(lat,is(30.0572));
        Double lon = points.get(51).getLon();
        assertThat(lon,is(107.874));

        Double lat2 = points.get(points.size()-1).getLat();
        assertThat(lat2,is(-29.609988));
        Double lon2 = points.get(points.size()-1).getLon();
        assertThat(lon2,is( 	28.233608));

    }
}
