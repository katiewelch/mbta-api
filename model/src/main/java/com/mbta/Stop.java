package com.mbta;

/*
 * Class that represents MBTA stop
 *  * id (String) - the id of the stop from the API
 *  * name (String) - the name of the stop from the API
 *  * routeID (String) - the ID of the route that this stop is on
 */
public class Stop {
    String id;
    String name;
    String routeID;

    public Stop(String ID, String n, String r) {
        id = ID;
        name = n;
        routeID = r;
    }

    public String getID() { return id; }

    public String getName() { return name; }

    public String getRouteID() { return routeID; }
}
