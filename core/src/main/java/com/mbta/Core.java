package com.mbta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Core {
    ArrayList<Route> routes;
    ArrayList<ArrayList<Stop>> stops;
    HashMap<String, ArrayList<Route>> connectingStops;
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

    void pullRoutes() {
        JSONObject obj = wrapper.getRoutes();


        assert(obj != null);
        JSONArray arr = (JSONArray) obj.get("data");
        for (int i = 0; i < arr.size(); i++) {
            JSONObject o = (JSONObject) arr.get(i);
            JSONObject o1 = (JSONObject) o.get("attributes");
            //System.out.println(o.get("id").toString());// + ", " + o1.get("long_name").toString())
            routes.add(new Route(o1.get("long_name").toString(), o.get("id").toString()));
        }

     /*   for (int j = 0; j < routes.size(); j++) {
            System.out.println(routes.get(j).getName() + ", " + routes.get(j).getID());
        }*/
    }

    //TODO PUT ACTUAL OBJECT IN connectingStops
    void pullStops() throws Exception {
        for (int i = 0; i < routes.size(); i++) {
            stops.add(new ArrayList<Stop>());

            Route currRoute = routes.get(i);
            JSONObject obj = wrapper.getStops(currRoute.getID());


            assert(obj != null);
            JSONArray arr = (JSONArray) obj.get("data");
          //  System.out.println(currRoute.getName() + "\n");
            for (int j = 0; j < arr.size(); j++) {
                JSONObject o = (JSONObject) arr.get(j);
                JSONObject o1 = (JSONObject) o.get("attributes");

                Stop newStop = new Stop(o.get("id").toString(), o1.get("name").toString(), currRoute.getID());
                stops.get(i).add(newStop);
                addConnectingStop(newStop, currRoute);
               // System.out.println(newStop.getName());
            }
           // System.out.println("\n");
        }

      /*  for (int k = 0; k < stops.size(); k++) {
            System.out.println(stops.get(k).size() + ", " + stops.get(k).get(0).getName());
        }    */    
    }

    void addConnectingStop(Stop stop, Route route1) throws Exception {
//        System.out.println("here");
        if (stopsMap.containsKey(stop.getName())) {
  //          System.out.println("!!!!");
            //TODO: add functionality for stops that connect 3 lines
      //      System.out.println(stop.getName() + "\n");
            Route route2 = stopsMap.get(stop.getName()).get(0);

            int indx1 = 0, indx2 = 0;
            try {
                indx1 = getRouteIndex(route1);
                indx2 = getRouteIndex(route2);
            } catch (IOException e) {
                System.out.println("addConnectingStop: Route ID not found in route");
                System.exit(0);
            }

            routes.get(indx1).addConnectingStop(route2, stop);
            routes.get(indx2).addConnectingStop(route1, stop);

            if (connectingStops.containsKey(stop.getName())) {
                connectingStops.get(stop.getName()).add(route1);
            } else {
                ArrayList<Route> r = new ArrayList<Route>();
                r.add(route1);
                r.add(route2);
                connectingStops.put(stop.getName(), r);
            }
        } else {
            //System.out.println(route1.getName() + " " + stop.getName());
            ArrayList<Route> arr = new ArrayList<Route>();
            arr.add(route1);
            stopsMap.put(stop.getName(), arr);
        }
    }

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

    int getRouteIndex(Route route) throws Exception {
        for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i).getID().equals(route.getID())) {
                return i;
            }
        }
        throw new Exception("addConnectingStop: Route ID not found in route");
    }
}
