package com.mbta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Hello world!
 *
 */
public class App 
{
    static Scanner in = new Scanner(System.in);
    static String input;
    static String options = "Please select an option:\n"
    + "1: List subway routes\n"
    + "2.1: Subway route with most stops\n"
    + "2.2: Subway route with least stops\n"
    + "2.3: List of stops connecting 2 or more routes\n"
    + "3: Directions\n";
    static ArrayList<Route> routes = new ArrayList<Route>();
    static ArrayList<ArrayList<Stop>> stops = new ArrayList<ArrayList<Stop>>();
    static Wrapper wrapper;
    static HashMap<String, ArrayList<String>> connectingStops = new HashMap<>();


    public static void main( String[] args ) throws Exception
    {
        //Wrapper mbtaWrapper = new Wrapper("https://api-v3.mbta.com");
        wrapper = new Wrapper();
        Model m = new Model();

        System.out.println("Welcome to Katie's MBTA decoder!\n");
        initialize();
        System.out.println("Enter 'o' at any time to see options\nEnter corresponding code to see desired output\n\n");
        System.out.println(options);
        
        while(true) {
            input = in.nextLine();
            decode(input, m);
            
        }
    }



    static void initialize() throws Exception {
        System.out.println("Initializing...\n");

        getRoutes();

        getStops();

    }

    static void getRoutes() {
        JSONObject obj = wrapper.getRoutes();


        assert(obj != null);
        JSONArray arr = (JSONArray) obj.get("data");
        for (int i = 0; i < arr.size(); i++) {
            JSONObject o = (JSONObject) arr.get(i);
            JSONObject o1 = (JSONObject) o.get("attributes");
            //System.out.println(o.get("id").toString());// + ", " + o1.get("long_name").toString())
            routes.add(new Route(o1.get("long_name").toString(), o.get("id").toString()));
        }

        for (int j = 0; j < routes.size(); j++) {
            System.out.println(routes.get(j).getName() + ", " + routes.get(j).getID());
        }
    }

    //TODO PUT ACTUAL OBJECT IN connectingStops
    static void getStops() throws Exception {
        for (int i = 0; i < routes.size(); i++) {
            stops.add(new ArrayList<Stop>());

            String currRoute = routes.get(i).getID();
            JSONObject obj = wrapper.getStops(currRoute);

            assert(obj != null);
            JSONArray arr = (JSONArray) obj.get("data");
            for (int j = 0; j < arr.size(); j++) {
                JSONObject o = (JSONObject) arr.get(i);
                JSONObject o1 = (JSONObject) o.get("attributes");
                //System.out.println(o.get("id").toString());// + ", " + o1.get("long_name").toString())
                String stopID = o.get("id").toString();
                stops.get(i).add(new Stop(stopID, o1.get("name").toString(), currRoute));
                addConnectingStop(stopID, currRoute);
            }
        }

        for (int k = 0; k < stops.size(); k++) {
            System.out.println(stops.get(k).size() + ", " + stops.get(k).get(0).getName());
        }        
    }

    static void addConnectingStop(String stopID, String route1) throws Exception {
        if (connectingStops.containsKey(stopID)) {
            //TODO: add functionality for stops that connect 3 lines
            String route2 = connectingStops.get(stopID).get(0);

            int indx1 = 0, indx2 = 0;
            try {
                indx1 = getRouteIndex(route1);
                indx2 = getRouteIndex(route2);
            } catch (IOException e) {
                System.out.println("addConnectingStop: Route ID not found in route");
                System.exit(0);
            }

            routes.get(indx1).addConnectingStop(route2, stopID);
            routes.get(indx2).addConnectingStop(route1, stopID);
        } else {
            System.out.println(route1 + " " + stopID);
            ArrayList<String> arr = new ArrayList<String>();
            arr.add(route1);
            connectingStops.put(stopID, arr);
        }
    }

    static void printConnectingStops() {
        Iterator<Map.Entry<String, ArrayList<String>>> itr = connectingStops.entrySet().iterator();
          
        while(itr.hasNext())
        {
             Map.Entry<String, ArrayList<String>> entry = itr.next();
             ArrayList<String> arr = entry.getValue();
             System.out.println(entry.getKey() + ": ");
             for (int i = 0; i < arr.size() - 1; i++) {
                System.out.println(arr.get(i) + ", ");
             }
             System.out.println(arr.get(arr.size() - 1) + "\n");
        }
    }

    static int getRouteIndex(String routeID) throws Exception {
        for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i).getID().equals(routeID)) {
                return i;
            }
        }
        throw new Exception("addConnectingStop: Route ID not found in route");
    }
    
    static void decode(String s, Model m) {
        switch(s) {
            case "o":
                System.out.println(options);
            case "1":
                for (int i = 0; i < routes.size(); i ++)
                    System.out.println(routes.get(i).getName());
                break;

            case "2.1":
                System.out.println(m.getRouteMostStops(stops));
                break;

            case "2.2":
                System.out.println(m.getRouteLeastStops(stops));
                break;

            case "2.3":
                printConnectingStops();
                break;

            case "3":
                System.out.println("Enter departing stop: ");
                String departing = in.nextLine();
                System.out.println("Enter arriving stop: ");
                String arriving = in.nextLine();
               // System.out.println(c.getDirections(departing, arriving));
                break;

            default:
                System.out.println("Incorrect entry -- Please try again. Enter 'o' for options");

        }
    }
}
