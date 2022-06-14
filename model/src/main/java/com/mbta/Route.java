package com.mbta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/*
 * Class that represents MBTA Route
 *  * name (String) - the long name of the route from the API
 *  * id (String) - the id of the route from the API
 *  * connectingStops (HashMap<Route, ArrayList<String>>) - hashmap that holds all connections from this route to other routes
 */
public class Route {
    String name;
    String id;
    //TODO i don't think this needs to be a list of Strings for the stops
    HashMap<Route, ArrayList<String>> connectingStops;

    public Route(String n, String ID) {
        name = n;
        id = ID;
        connectingStops = new HashMap<>();
    }

    public String getName() { return name; }

    public String getID() { return id; }

    public HashMap<Route, ArrayList<String>> getConnectingStops() { return connectingStops; }

    //adds Route and Stop that connects this route to given route to hashmap of connecting stops
    public void addConnectingStop(Route route, Stop stop) {
        if (connectingStops.containsKey(route)) {
            connectingStops.get(route).add(stop.getName());
        } else {
            ArrayList<String> arr = new ArrayList<String>();
            arr.add(stop.getName());
            connectingStops.put(route, arr);
        }
    }

    //determines if given route is connected to this route
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

    /*
     * returns stop(s) that connects this route to given route, if any TODO update this note is i change connectingStops hashmap
     * connectsTo(Route r) should be called before this to ensure conneciton exists
     * TODO should this dail is connection doesn't exist
     */
    public ArrayList<String> getConnectingStopValue(Route route) {
        return connectingStops.get(route);
    } 
}
