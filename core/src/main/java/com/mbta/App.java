package com.mbta;

import java.util.ArrayList;
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


    public static void main( String[] args )
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



    static void initialize() {
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

    static void getStops() {
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
                stops.get(i).add(new Stop(o.get("id").toString(), o1.get("name").toString(), currRoute));
            }
        }

        for (int k = 0; k < stops.size(); k++) {
            System.out.println(stops.get(k).size() + ", " + stops.get(k).get(0).getName());
        }        
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
                //c.getConnectingStops();
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
