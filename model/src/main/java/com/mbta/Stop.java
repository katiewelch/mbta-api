package com.mbta;

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
