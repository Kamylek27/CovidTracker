package com.kamil.controller;

import com.kamil.model.LocationStats;
import com.kamil.model.Point;
import com.kamil.service.CovidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    private CovidService covidService;

    @Autowired
    public HomeController(CovidService covidService) {
        this.covidService = covidService;
    }

    @GetMapping("/")
    public String allStats(Model model) throws IOException, InterruptedException {
        List<LocationStats> stats = covidService.virusStats();

        int sumTotalCases = stats.stream()
                .mapToInt(e->e.getLatestTotalCases())
                .sum();
        int beforeDaySum = stats.stream()
                .mapToInt(e->e.getDayBeforeTotalCases())
                .sum();

        model.addAttribute("sumtotalcases",sumTotalCases);
        model.addAttribute("beforedaysumtotalcases",beforeDaySum);
        model.addAttribute("stats",stats);
        return "stats";
    }

    @GetMapping("/map")
    public String mapStats(Model model) throws IOException, InterruptedException {
        List<Point> points = covidService.virusPoint();
        model.addAttribute("point",points);


        return "map";
    }



}
