package com.mbta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

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
     * determines which routes to take to get from d (route from given departure stop) to a (route from given arrival stop)
     */
    public String getDirections(Route d, Route a) {
        if (d.equals(a)) { //if they are on the same route, just return route
            return " --> " + d.getName();
        } else if (d.connectsTo(a)) {
            return " --> " + d.getName() + ", " + a.getName();
        } else {
            Iterator<Map.Entry<Route, ArrayList<String>>> itr = d.getConnectingStops().entrySet().iterator();
            
            while(itr.hasNext())
            {
                Route r = itr.next().getKey();
                if (a.connectsTo(r)) {
                    return " --> " + d.getName() + ", " + r.getName() + ", " + a.getName();
                }
            }
        }
        return "";
    }
}
