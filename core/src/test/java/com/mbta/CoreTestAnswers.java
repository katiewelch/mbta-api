package com.mbta;

import java.util.ArrayList;
import java.util.Arrays;

public class CoreTestAnswers {
    Route redRoute = new Route("Red Line", "Red");
    Route greenERoute = new Route("Green Line E", "Green-E");
    Route orangeRoute = new Route("Orange Line", "Orange");

    Stop downtownCrossingRed = new Stop("place-dwnxg", "Downtown Crossing", "Red");
    Stop downtownCrossingOrange = new Stop("place-dwnxg", "Downtown Crossing", "Orange");
    Stop parkStRed = new Stop("place-pktrm", "Park Street", "Red");
    Stop parkStGreen = new Stop("place-pktrm", "Park Street", "Green-E");
    Stop haymarketOrange = new Stop("place-haecl", "Haymarket", "Orange");
    Stop haymarketGreen = new Stop("place-haecl", "Haymarket", "Green-E");
    Stop northStationOrange = new Stop("place-north", "North Station", "Orange");
    Stop northStationGreen = new Stop("place-north", "North Station", "Green-E");
    Stop northeastern = new Stop("place-nuniv", "Northeastern University", "Green-E");
    Stop kendallMIT = new Stop("place-knncl", "Kendall/MIT", "Red");
    Stop roxburyCrossing = new Stop("place-rcmnl", "Roxbury Crossing", "Orange");

    ArrayList<Route> routeAns = new ArrayList<Route>(
        Arrays.asList(redRoute, greenERoute, orangeRoute));

    ArrayList<Stop> redStops = new ArrayList<Stop>(
        Arrays.asList(downtownCrossingRed, parkStRed, kendallMIT));

    ArrayList<Stop> orangeStops = new ArrayList<Stop>(
        Arrays.asList(downtownCrossingOrange, haymarketOrange, northStationOrange, roxburyCrossing));

    ArrayList<Stop> greenEStops = new ArrayList<Stop>(
        Arrays.asList(parkStGreen, haymarketGreen, northStationGreen, northeastern));

}