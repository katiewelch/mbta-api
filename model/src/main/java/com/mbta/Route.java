package com.mbta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

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

    public ArrayList<String> getConnectingStopValue(Route route) {
        return connectingStops.get(route);
    } 

    public HashMap<Route, ArrayList<String>> getConnectingStops() { return connectingStops; }

    public boolean connectsTo(Route r) {
        Iterator<Entry<Route, ArrayList<String>>> itr = connectingStops.entrySet().iterator();
          
        while(itr.hasNext())
        {
            if (itr.next().getKey().getName().equals(r.getName())) {
                return true;
             }
        }
        return false;
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
