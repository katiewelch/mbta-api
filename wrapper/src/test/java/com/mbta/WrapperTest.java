package com.mbta;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

/**
 * Unit tests for Wrapper class
 */
public class WrapperTest 
{
    Wrapper w;
    WrapperTestAnswers wAns;
    JSONObject routeObj;
    JSONObject redStopsObj;
    JSONObject mattapanStopsObj;

    public WrapperTest() {
        w = new Wrapper();
        wAns = new WrapperTestAnswers();

        routeObj = w.getRoutes();
        redStopsObj = w.getStops("Red");
        mattapanStopsObj = w.getStops("Mattapan");
    }

    @Test
    public void getRoutesTest()
    {
        //parse data from JSONObject pulled from API
        JSONArray arr = (JSONArray) routeObj.get("data");
        ArrayList<String> routeNames = new ArrayList<String>();
        ArrayList<String> routeID = new ArrayList<String>();

        for (int i = 0; i < arr.size(); i++) {
            JSONObject o = (JSONObject) arr.get(i);
            JSONObject o1 = (JSONObject) o.get("attributes");
            routeNames.add(o1.get("long_name").toString());
            routeID.add(o.get("id").toString());
        }

        //query only pulled routes of type 0, 1
        //check that we were able to find 8 routes total, 5 Light Rail, 3 Heavy Rail
        assertTrue(arr.size() == 8);
        assertTrue(routeNames.size() == 8);
        assertTrue(routeID.size() == 8);

        //check all of the names are the same
        for (int j = 0; j < routeNames.size(); j ++) {
            assertTrue(routeNames.contains(wAns.routeNameAns.get(j)));
        }

        //check all of the ids are the same
        for (int j = 0; j < routeNames.size(); j ++) {
            assertTrue(routeID.contains(wAns.routeIDAns.get(j)));
        }
    }

    //test for Heavy Rail
    @Test 
    public void getRedStopsTest() {
        //parse data from JSONObject pulled from API
        assert(redStopsObj != null);
        JSONArray arr = (JSONArray) redStopsObj.get("data");
        ArrayList<String> redStopNames = new ArrayList<String>();
        ArrayList<String> redStopID = new ArrayList<String>();

        for (int j = 0; j < arr.size(); j++) {
            JSONObject o = (JSONObject) arr.get(j);
            JSONObject o1 = (JSONObject) o.get("attributes");

            redStopNames.add(o1.get("name").toString());
            redStopID.add(o.get("id").toString());
        }

        //check that we were able to find 22 stops on red line
        assertTrue(redStopNames.size() == 22);
        assertTrue(redStopID.size() == 22);

        //check all of the names are the same
        for (int j = 0; j < redStopNames.size(); j ++) {
            assertTrue(redStopNames.contains(wAns.redStopsNameAns.get(j)));
        }

        //check all of the ids are the same
        //ans array does not contain all stop ID
        for (int j = 0; j < wAns.redStopsIDAns.size(); j ++) {
            assertTrue(redStopID.contains(wAns.redStopsIDAns.get(j)));
        }
    }

    //test for light rail
    @Test 
    public void getMattapanStopsTest() {
        //parse data from JSONObject pulled from API
        assert(mattapanStopsObj != null);
        JSONArray arr = (JSONArray) mattapanStopsObj.get("data");
        ArrayList<String> mattapanStopNames = new ArrayList<String>();
        ArrayList<String> mattapanStopID = new ArrayList<String>();

        for (int j = 0; j < arr.size(); j++) {
            JSONObject o = (JSONObject) arr.get(j);
            JSONObject o1 = (JSONObject) o.get("attributes");

            mattapanStopNames.add(o1.get("name").toString());
            mattapanStopID.add(o.get("id").toString());
        }

        //check that we were able to find 8 stops on mattapan line
        assertTrue(mattapanStopNames.size() == 8);
        assertTrue(mattapanStopID.size() == 8);

        //check all of the names are the same
        for (int j = 0; j < mattapanStopNames.size(); j ++) {
            assertTrue(mattapanStopNames.contains(wAns.mattapanStopsNameAns.get(j)));
        }

        //check all of the ids are the same
        for (int j = 0; j < mattapanStopID.size(); j ++) {
            System.out.println(mattapanStopID.get(j));
            assertTrue(mattapanStopID.contains(wAns.mattapanStopsIDAns.get(j)));
        }
    }
}
