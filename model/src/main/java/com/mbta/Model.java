package com.mbta;

import java.util.ArrayList;

public class Model {
    public Model() {}

    public String getRouteMostStops(ArrayList<ArrayList<Stop>> stops) {
        String retRoute = "";
        int retCount = 0;

        for (int i = 0; i < stops.size(); i++) {
            int currStopSize = stops.get(i).size();
            if (currStopSize > retCount) {
                retCount = currStopSize;
                retRoute = stops.get(i).get(0).getRouteID();
            }
        }

        String retString = retRoute + ": " + retCount;
        return retString;
    }

    public String getRouteLeastStops(ArrayList<ArrayList<Stop>> stops) {
        String retRoute = "";
        int retCount = Integer.MAX_VALUE;

        for (int i = 0; i < stops.size(); i++) {
            int currStopSize = stops.get(i).size();
            if (currStopSize < retCount) {
                retCount = currStopSize;
                retRoute = stops.get(i).get(0).getRouteID();
            }
        }

        String retString = retRoute + ": " + retCount;
        return retString;
    }

    public String getDirections(String d, String a, ArrayList<Route> r, ArrayList<ArrayList<Stop>> s) {
        Route dRoute = null;
        Route aRoute = null;
        try {
            dRoute = r.get(getRouteIndex(d, s));
            aRoute = r.get(getRouteIndex(a, s));
            
        } catch (Exception e) {
            //TODO: handle exception
        }

        if (dRoute.equals(aRoute)) {
            return d + " to " + a + " --> " + dRoute.getName();
        } else if (dRoute.connectsTo(aRoute)) {
            return d + " to " + a + " --> " + dRoute.getName() + ", " + aRoute.getName();
        }
        //TODO fix and add functionality for 3 line trips
        return "no";
    }

    //TODO update exceptions to allow for new input rather than quitting
    int getRouteIndex(String x, ArrayList<ArrayList<Stop>> s) throws Exception {
        for (int i = 0; i < s.size(); i++) {
            for (int j = 0; j < s.get(i).size(); j++) {
                if (x.equals(s.get(i).get(j).getName())) {
                    return i;
                }
            }
        }
        throw new Exception("Invalid Stop");
    }
/* 
    static int getRouteIndex(String route, ArrayList<Route> r) throws Exception {
        for (int i = 0; i < r.size(); i++) {
            if (r.get(i).getName().equals(route)) {
                return i;
            }
        }
        throw new Exception("addConnectingStop: Route ID not found in route");
    } */
}
