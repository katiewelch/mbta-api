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
}
