package com.mbta;

import java.util.ArrayList;

/*
 * Class that does computations of all (route and stop) data
 */
public class Model {
    public Model() {}

    //return route (out of type 0 and 1) that has the most stops as well as the stop count
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

        //string created for command line
        String retString = retRoute + ": " + retCount;
        return retString;
    }

    //return route (out of type 0 and 1) that has the least stops as well as the stop count
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

        //string created for command line
        String retString = retRoute + ": " + retCount;
        return retString;
    }

    /* 
     * determines which routes to take to get from d (given departure stop) to a (given arrival stop)
     * d and a are given as strings representing the stop's name TODO change to id?
     */
    public String getDirections(String d, String a, ArrayList<Route> r, ArrayList<ArrayList<Stop>> s) {
        Route dRoute = null;
        Route aRoute = null;
        try {
            dRoute = r.get(getRouteIndex(d, s));
            aRoute = r.get(getRouteIndex(a, s));
            
        } catch (Exception e) {
            //TODO: handle exception, can we make it a -1 one thing
        }

        if (dRoute.equals(aRoute)) { //if they are on the same route, just return route
            return d + " to " + a + " --> " + dRoute.getName();
        } else if (dRoute.connectsTo(aRoute)) {
            return d + " to " + a + " --> " + dRoute.getName() + ", " + aRoute.getName();
        }
        //TODO fix and add functionality for 3 line trips also change this string
        return "no";
    }

    //TODO update exceptions to allow for new input rather than quitting
    //TODO combine this with getRouteIndex in core
    int getRouteIndex(String x, ArrayList<ArrayList<Stop>> s) throws Exception {
        for (int i = 0; i < s.size(); i++) {
            for (int j = 0; j < s.get(i).size(); j++) {
                if (x.equals(s.get(i).get(j).getName())) {
                    return i;
                }
            }
        }
        //TODO instead return -1
        throw new Exception("Invalid Stop");
    }
}
