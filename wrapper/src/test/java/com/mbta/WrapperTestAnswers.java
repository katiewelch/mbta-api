package com.mbta;

import java.util.ArrayList;
import java.util.Arrays;

//variables representing answers for Wrapper tests
//separate file for cleanliness purposes
public class WrapperTestAnswers {
  ArrayList<String> routeNameAns = new ArrayList<String>(
    Arrays.asList("Red Line", "Orange Line", "Blue Line", "Green Line B", "Green Line C", "Green Line D", "Green Line E", "Mattapan Trolley"));

  ArrayList<String> routeIDAns = new ArrayList<String>(
    Arrays.asList("Red", "Orange", "Blue", "Green-B", "Green-C", "Green-D", "Green-E", "Mattapan"));

  ArrayList<String> redStopsNameAns = new ArrayList<String>(
    Arrays.asList("Alewife", "Davis", "Porter", "Harvard", "Central", "Kendall/MIT", "Charles/MGH", "Park Street", "Downtown Crossing", "South Station", "Broadway", "Andrew", "JFK/UMass", "Savin Hill", "Fields Corner", "Shawmut", "Ashmont", "North Quincy", "Wollaston", "Quincy Center", "Quincy Adams", "Braintree"));

  ArrayList<String> redStopsIDAns = new ArrayList<String>(
    Arrays.asList("place-alfcl", "place-cntsq", "place-sstat", "place-andrw", "place-fldcr", "place-wlsta", "place-brntn"));

  ArrayList<String> mattapanStopsNameAns = new ArrayList<String>(
    Arrays.asList("Ashmont", "Cedar Grove", "Butler", "Milton", "Central Avenue", "Valley Road", "Capen Street", "Mattapan"));

  ArrayList<String> mattapanStopsIDAns = new ArrayList<String>(
    Arrays.asList("place-asmnl", "place-cedgr", "place-butlr", "place-miltt", "place-cenav", "place-valrd", "place-capst", "place-matt"));
}
