package com.mbta;

import java.util.Scanner;

/**
 * Main Class
 * 
 * Connects core and model of program to user/command line
 */
public class App 
{
    static Core core;
    static Model model;
    static String options = "Please select an option:\n"
    + "1: List subway routes\n"
    + "2.1: Subway route with most stops\n"
    + "2.2: Subway route with least stops\n"
    + "2.3: List of stops connecting 2 or more routes\n"
    + "3: Directions\n";
    static Scanner in = new Scanner(System.in);
    static String input;


    public static void main( String[] args ) {
        model = new Model();
        core = new Core();

        System.out.println("Welcome to Katie's MBTA decoder!\n");
        initialize();
        System.out.println("Enter 'o' at any time to see options\nEnter corresponding code to see desired output\n\n");
        System.out.println(options);
        
        while(true) {
            input = in.nextLine();
            decode(input, model);
            System.out.println("\n");
        }
    }

    //Pulls route and stop information for Light (type 0) and Heavy (type 1) rails
    //data will be saved in core to be accessed on demand
    static void initialize() {
        System.out.println("Initializing...\n");

        core.parseRoutes();
        core.parseStops();
    }
    
    //command line interface
    static void decode(String s, Model m) {
        switch(s) {
            case "o": //print options
                System.out.println(options);
                break;

            case "1": //get routes
                for (int i = 0; i < core.getRoutes().size(); i ++)
                    System.out.println(core.getRoutes().get(i).getName());
                break;

            case "2.1": //get route with most stops
                System.out.println(m.getRouteMostStops(core.getStops()));
                break;

            case "2.2": //get route with lease stop
                System.out.println(m.getRouteLeastStops(core.getStops()));
                break;

            case "2.3": //get stops that connect 2 or more rails
                core.printConnectingStops();
                break;

            case "3": //return directions from one stop to another
                System.out.println("Enter departing stop: ");
                String departing = in.nextLine();
                System.out.println("Enter arriving stop: ");
                String arriving = in.nextLine();

                Route dRoute = core.getRouteByStopName(departing);
                Route aRoute = core.getRouteByStopName(arriving);
                if (dRoute == null) {
                    System.out.println("Departing route not found, please check spelling and try again");
                    return;
                }
                if (aRoute == null) {
                    System.out.println("Arriving route not found, please check spelling and try again");
                    return;
                }
                System.out.println(departing + " to " + arriving + m.getDirections(dRoute, aRoute));
                break;

            default: //incorrect input
                System.out.println("Incorrect entry -- Please try again. Enter 'o' for options");
                break;
        }
    }
}
