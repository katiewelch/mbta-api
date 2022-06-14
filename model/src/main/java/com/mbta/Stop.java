package com.mbta;

import javax.print.DocFlavor.STRING;

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

    /* 
     * Overriding equals method to compare by value rather than object
     * Mostly used for testing purposed
     */ 
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }

        if (!(o instanceof Stop)) { return false; }

        Stop s = (Stop) o;

        return this.getName().equals(s.getName()) && this.getID().equals(s.getID()) && this.getRouteID().equals(s.getRouteID()); 
    } 
}
