package com.mbta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
 * Controller class
 * 
 * holds data
 *  * routes (ArrayList<Route>) - list of all Light (type 0) and Heavy (type 1) rails held in Route class with name, id, and connections
 *  * stops (ArrayList<ArrayList<Stop>>) - matrix of all stops. stops are organized by route, where index of each route matches index of each route matches index in routes variable
 *  * connectingStops (HashMap<String, ArrayList<Route>>) - map of all stops that connect 2 or more routes, mapped with ArrayList of routes they are on
 * TODO * stopsMap (HashMap<String, ArrayList<Route>>) -
 *  * wrapper (Wrapper) - instance of class that communicates with MBTA API
 */
public class Core {
    ArrayList<Route> routes;
    ArrayList<ArrayList<Stop>> stops;
    HashMap<String, ArrayList<Route>> connectingStops;
    //TODO is this necessary? its the same strucutre as above, think it should just be Route, not ArrayList<Route>
    HashMap<String, ArrayList<Route>> stopsMap;
    static Wrapper wrapper;
    
    public Core() {
        routes = new ArrayList<Route>();
        stops = new ArrayList<ArrayList<Stop>>();
        connectingStops = new HashMap<>();
        stopsMap = new HashMap<>();
        wrapper = new Wrapper();
    }

    ArrayList<Route> getRoutes() { return routes; }

    ArrayList<ArrayList<Stop>> getStops() { return stops; }

    //parses route data pulled from API by wrapper, saves data in routes variable as Route object with name and id
    void parseRoutes() {
        JSONObject obj = wrapper.getRoutes();

        assert(obj != null);
        JSONArray arr = (JSONArray) obj.get("data");
        for (int i = 0; i < arr.size(); i++) {
            JSONObject o = (JSONObject) arr.get(i);
            JSONObject o1 = (JSONObject) o.get("attributes");
            routes.add(new Route(o1.get("long_name").toString(), o.get("id").toString()));
        }
    }

    //TODO PUT ACTUAL OBJECT IN connectingStops
    /* parses stop data pulled from API by wrapper, saves data in stops variable as Stop with name, id, and route it's on
     *      if a stop is on two routes, it will be saved as two separate objects, with connection info saved in connectingStops variable as well as each route
     * throws Exception if TODO
    */
    void parseStops() throws Exception {
        for (int i = 0; i < routes.size(); i++) {
            stops.add(new ArrayList<Stop>());

            Route currRoute = routes.get(i);
            JSONObject obj = wrapper.getStops(currRoute.getID());

            assert(obj != null);
            JSONArray arr = (JSONArray) obj.get("data");

            for (int j = 0; j < arr.size(); j++) {
                JSONObject o = (JSONObject) arr.get(j);
                JSONObject o1 = (JSONObject) o.get("attributes");

                Stop newStop = new Stop(o.get("id").toString(), o1.get("name").toString(), currRoute.getID());
                stops.get(i).add(newStop);
                addConnectingStop(newStop, currRoute);
            }
        }
    }

    /*
     * When connecting stop is found, connection is added to each respective route, and to the connentingStops map
     */
    void addConnectingStop(Stop stop, Route route1) throws Exception {
        if (stopsMap.containsKey(stop.getName())) {
            //TODO: add functionality for stops that connect 3 lines
            Route route2 = stopsMap.get(stop.getName()).get(0);

            //each respective route's index in route variable, index is used to find route's stops in stops matrix
            int indx1 = 0, indx2 = 0;
            try {
                indx1 = getRouteIndex(route1);
                indx2 = getRouteIndex(route2);
            } catch (IOException e) {
                System.out.println("addConnectingStop: Route ID not found in route");
                System.exit(0);
            }

            //update route object to hold route it connects to and stop that connects them
            routes.get(indx1).addConnectingStop(route2, stop);
            routes.get(indx2).addConnectingStop(route1, stop);

            //following functionality allows for one stop to connect 3 or more routes
            if (connectingStops.containsKey(stop.getName())) {
                connectingStops.get(stop.getName()).add(route1);
            } else {
                ArrayList<Route> r = new ArrayList<Route>();
                r.add(route1);
                r.add(route2);
                connectingStops.put(stop.getName(), r);
            }
        } else {
            //if stop has not already been seen, add current route to object
            ArrayList<Route> arr = new ArrayList<Route>();
            arr.add(route1);
            stopsMap.put(stop.getName(), arr);
        }
    }

    /*
     * function for App class to communicate with command line 
     * prints connection stop, along with routes that it connects
     * TODO can this be moved to app class?
     */
    void printConnectingStops() {
        Iterator<Map.Entry<String, ArrayList<Route>>> itr = connectingStops.entrySet().iterator();
          
        while(itr.hasNext())
        {
             Map.Entry<String, ArrayList<Route>> entry = itr.next();
             ArrayList<Route> arr = entry.getValue();
             System.out.println(entry.getKey() + ": ");
             for (int i = 0; i < arr.size() - 1; i++) {
                System.out.print(arr.get(i).getName() + ", ");
             }
             System.out.println(arr.get(arr.size() - 1).getName() + "\n");
        }
    }

    /*
     * returns index of given route in route variable
     * if no route is found, returns -1. calling method with be aborted, user will get error and have ability to select new option
     * index can be used to find given route's stop in stops matrix
     * TODO return -1
     */
    int getRouteIndex(Route route) throws Exception {
        for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i).getID().equals(route.getID())) {
                return i;
            }
        }
        //TODO have this return -1, and update other methods to respond accordingly which is to just go back to options
        throw new Exception("addConnectingStop: Route ID not found in route");
    }
}
