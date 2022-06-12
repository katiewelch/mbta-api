package com.mbta;

import java.util.ArrayList;
import java.util.HashMap;

public class Route {
    String name;
    String id;
    HashMap<String, ArrayList<String>> connectingStops;

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

    public void addConnectingStop(String routeID, String stopID) {
        if (connectingStops.containsKey(routeID)) {
            connectingStops.get(routeID).add(stopID);
        } else {
            ArrayList<String> arr = new ArrayList<String>();
            arr.add(stopID);
            connectingStops.put(routeID, arr);
        }
    }
}
