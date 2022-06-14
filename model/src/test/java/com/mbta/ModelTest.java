package com.mbta;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

/**
 * Unit tests for simple classes in the Model package.
 * Includes Route, and Stop classes
 * The Model class and it's methods are tested within the Core tests
 */
public class ModelTest 
{
    Route r1;
    Route r2;
    Stop s11;
    Stop s12;
    Stop s13;
    Stop s21;
    Stop s22;
    Stop s23;

    //Constructor that sets up tests
    public ModelTest() {
        r1 = new Route("Green Line B", "Green-B");
        r2 = new Route ("Red Line", "Red");

        s11 = new Stop("place-pktrm","Park Street",  "Green-B");
        s12 = new Stop("place-gover","Government Center",  "Green-B");
        s13 = new Stop("place-coecl","Copley",  "Green-B");
        s21 = new Stop("place-alfcl","Alewife",  "Red");
        s22 = new Stop("place-pktrm","Park Street",  "Red");
        s23 = new Stop("place-knncl", "Kendall/MIT", "Red");
    }

    //Connecting Stop Tests
    @Test
    public void connectingStopsTest() {
        //s11 and s22 (Park Street) is on both Red and Green lines
        //add as a connenting stop
        assertTrue(s11.getID().equals(s22.getID()));
        r1.addConnectingStop(r2, s11);
        r2.addConnectingStop(r1, s22);

        //check getConnectingStop() returns values we just added
        HashMap<Route, ArrayList<String>> h1 = r1.getConnectingStops();
        HashMap<Route, ArrayList<String>> h2 = r2.getConnectingStops();

        assertTrue(h1.containsKey(r2));
        assertTrue(h1.get(r2).get(0).equals(s11.getName()));
        assertTrue(h2.containsKey(r1));
        assertTrue(h2.get(r1).get(0).equals(s22.getName()));

        //check getValue() returns stop that connects routes
        assertTrue(r1.getConnectingStopValue(r2).size() == 1);
        assertTrue(r1.getConnectingStopValue(r2).get(0).equals(s11.getName()));
        assertTrue(r2.getConnectingStopValue(r1).size() == 1);
        assertTrue(r2.getConnectingStopValue(r1).get(0).equals(s22.getName()));

        //ensure routes are connected
        assertTrue(r1.connectsTo(r2));
        assertTrue(r2.connectsTo(r1));
    }

    //Route Getters
    @Test
    public void routeGettersTest() {
        assertTrue(r1.getName().equals("Green Line B"));
        assertTrue(r2.getName().equals("Red Line"));
        assertTrue(r1.getID().equals("Green-B"));
        assertTrue(r2.getID().equals("Red"));
    }

    //Stop Getters
    @Test
    public void getStopNameTest() {
        assertTrue(s11.getName().equals("Park Street"));
        assertTrue(s12.getName().equals("Government Center"));
        assertTrue(s13.getName().equals("Copley"));
        assertTrue(s21.getName().equals("Alewife"));
        assertTrue(s22.getName().equals("Park Street"));
        assertTrue(s23.getName().equals("Kendall/MIT"));
    }

    @Test
    public void getStopIDTest() {
        assertTrue(s11.getID().equals("place-pktrm"));
        assertTrue(s12.getID().equals("place-gover"));
        assertTrue(s13.getID().equals("place-coecl"));
        assertTrue(s21.getID().equals("place-alfcl"));
        assertTrue(s22.getID().equals("place-pktrm"));
        assertTrue(s23.getID().equals("place-knncl"));
    }

    @Test
    public void getStopRouteTest() {
        assertTrue(s11.getRouteID().equals("Green-B"));
        assertTrue(s12.getRouteID().equals("Green-B"));
        assertTrue(s13.getRouteID().equals("Green-B"));
        assertTrue(s21.getRouteID().equals("Red"));
        assertTrue(s22.getRouteID().equals("Red"));
        assertTrue(s23.getRouteID().equals("Red"));
    }
}
