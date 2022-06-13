package com.mbta;

import java.util.ArrayList;
import java.util.HashMap;

public class Route {
    String name;
    String id;
    HashMap<Route, ArrayList<String>> connectingStops;

    public Route(String n, String ID) {
        name = n;
        id = ID;
        connectingStops = new HashMap<>();
    }

    public String getName() { return name; }

    public String getID() { return id; }

    public ArrayList<String> getConnectingStopValue(String route) {
        return connectingStops.get(route);
    } 

    public void addConnectingStop(Route route, Stop stop) {
        if (connectingStops.containsKey(route)) {
            connectingStops.get(route).add(stop.getName());
        } else {
            ArrayList<String> arr = new ArrayList<String>();
            arr.add(stop.getName());
            connectingStops.put(route, arr);
        }
    }
}
