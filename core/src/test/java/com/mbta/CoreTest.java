package com.mbta;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Unit tests for simple Core package
 * includes tests for Core classes
 *  * App class is tested through Core tests
 */
public class CoreTest {    
    Core c;
    CoreTestAnswers cAns;
    ArrayList<Route> routes;
    ArrayList<ArrayList<Stop>> stops;


    public CoreTest() throws Exception {
        c = new Core();
        c.parseRoutes();
        c.parseStops();
        routes = c.getRoutes();
        stops = c.getStops();

        cAns = new CoreTestAnswers();
    }

    /*
     * Connecting Stop tests
     * testing that connections were added correctly
     */
    @Test
    public void redOrangeConnectionTest() throws Exception {
        int redIdx = c.getRouteIndex(cAns.redRoute);
        int orangeIdx = c.getRouteIndex(cAns.orangeRoute);
        assertTrue(routes.get(redIdx).getConnectingStopValue(routes.get(orangeIdx)).get(0).equals(cAns.downtownCrossingOrange.getName()));
        assertTrue(routes.get(orangeIdx).getConnectingStopValue(routes.get(redIdx)).get(0).equals(cAns.downtownCrossingRed.getName()));
    }

    @Test
    public void redGreenEConnectionTest() throws Exception {
        int redIdx = c.getRouteIndex(cAns.redRoute);
        int greenIdx = c.getRouteIndex(cAns.greenERoute);
        assertTrue(routes.get(redIdx).getConnectingStopValue(routes.get(greenIdx)).get(0).equals(cAns.parkStGreen.getName()));
        assertTrue(routes.get(greenIdx).getConnectingStopValue(routes.get(redIdx)).get(0).equals(cAns.parkStRed.getName()));
    }

    //orange and green E connect at two stops
    @Test
    public void orangeGreenEConnectionTest() throws Exception {
        int orangeIdx = c.getRouteIndex(cAns.orangeRoute);
        int greenIdx = c.getRouteIndex(cAns.greenERoute);
        //checking haymarket
        assertTrue(routes.get(orangeIdx).getConnectingStopValue(routes.get(greenIdx)).contains(cAns.haymarketGreen.getName()));
        assertTrue(routes.get(greenIdx).getConnectingStopValue(routes.get(orangeIdx)).contains(cAns.haymarketOrange.getName()));
        //checking north station
        assertTrue(routes.get(orangeIdx).getConnectingStopValue(routes.get(greenIdx)).contains(cAns.northStationGreen.getName()));
        assertTrue(routes.get(greenIdx).getConnectingStopValue(routes.get(orangeIdx)).contains(cAns.northStationOrange.getName()));
    }
    
    /**
     * Getter Tests
     */
    @Test
    public void getRoutesTest() {
        for (int i = 0; i < cAns.routeAns.size(); i++) {
            assertTrue(routes.contains(cAns.routeAns.get(i)));
        }
    }

    @Test
    public void getRouteIndexTest() throws Exception {
        //0, 2, 6 come from MBTA API as seen on Swagger UI
        assertTrue(c.getRouteIndex(cAns.redRoute) == 0);
        assertTrue(c.getRouteIndex(cAns.orangeRoute) == 2);
        assertTrue(c.getRouteIndex(cAns.greenERoute) == 6);
    }

    @Test
    public void getRedStopsTest() throws Exception {
        int routeIdx = c.getRouteIndex(cAns.redRoute);
        ArrayList<Stop> redStops = stops.get(routeIdx);

        System.out.println(routeIdx + " " + redStops.size() + " " + cAns.redStops.size());
        for (int i = 0; i < cAns.redStops.size(); i++) {
            assertTrue(redStops.contains(cAns.redStops.get(i)));
        }
    }
    
    @Test
    public void getOrangeStopsTest() throws Exception {
        int routeIdx = c.getRouteIndex(cAns.orangeRoute);
        ArrayList<Stop> orangeStops = stops.get(routeIdx);

        for (int i = 0; i < cAns.orangeStops.size(); i++) {
            assertTrue(orangeStops.contains(cAns.orangeStops.get(i)));
        }
    }

    @Test
    public void getGreenEStopsTest() throws Exception {
        int routeIdx = c.getRouteIndex(cAns.greenERoute);
        ArrayList<Stop> greenEStops = stops.get(routeIdx);

        for (int i = 0; i < cAns.greenEStops.size(); i++) {
            assertTrue(greenEStops.contains(cAns.greenEStops.get(i)));
        }
    }
}