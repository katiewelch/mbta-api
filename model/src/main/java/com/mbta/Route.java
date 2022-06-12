package com.mbta;

public class Route {
    String name;
    String id;

    public Route(String n, String ID) {
        name = n;
        id = ID;
    }

    public String getName() { return name; }

    public String getID() { return id; }
}
