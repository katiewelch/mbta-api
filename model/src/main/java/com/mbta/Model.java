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
    public String getDirections(Route d, Route a) {
        if (d.equals(a)) { //if they are on the same route, just return route
            return d.getName() + " to " + a + " --> " + d.getName();
        } else if (d.connectsTo(a)) {
            return " --> " + d.getName() + ", " + a.getName();
        }
        //TODO fix and add functionality for 3 line trips also change this string
        return "";
    }
}
