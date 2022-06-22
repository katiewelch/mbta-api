# MBTA API
TODO
general organzing
    same fonts boldness etc for classes, methods, variables
make diagram of classes
move wrapper section above core
draw indices for stop and routes


## Overview
This program communicates with the [MBTA's](https://mbta.com/) [API](https://api-v3.mbta.com/docs/swagger/index.html#/) to parse data and create a command line interface where users can get information regarding routes and stops.

## Project Setup
### Core
The core package of this project acts as the controller of the program. It mediates communication between the user and the body of the program: Model and Wrapper.

**App** <br>
The App class contains the main method as well as two other methods, one that gets and parses through the API data and one that communicate with the user

*main( String[] args)* <br>
The main method creates instances of the Model and Core classes, prints a message to the user, and initializes the data in the program. It contains a while loop that constantly scans for user input on the command line, and sends the input to the decode(String s, Model m) method to be properly dealt with.

*initialize()* <br>
This method calls parseRoutes() and parseStops() from the Core class. It is only called once at the beginning of the program and pulls all the route and stop data from the MBTA API and stores it in arrays within the Core class. The data is stored in the Core to limit the number of API calls throughout the program and increase the efficiency each time the user makes a command line request. While it does make the start up time of the program longer and requires the Core instance to hold the data within memory, neither of these are large enough to have an effect on the program.

*decode(String s, Model m)* <br>
The decode method deals with all command line input from the user. The command line options are listed below and can be shown by typing "o" in the command line at any time.

> Please select an option: <br>
> 1: List subway routes <br>
> 2.1: Subway route with most stops <br>
> 2.2: Subway route with least stops <br>
> 2.3: List of stops connecting 2 or more routes <br>
> 3: Directions <br>
> o: Show options <br>
> e: Exit

The method runs a switch loop that calls the correct method in either the core (fetch data) or the model (compute data) and prints out the answer to the command line. If an incorrect option is entered, the user will get an error message and can input a new option code.

**Core**
The Core and App classes work together to act as a controller for the program. The App class focuses on the front end and communicating between the user and the program while the Core acts as the main controller for the backend mediating communication between the data, the Wrapper (API), and the Model.

*parseRoutes()* <br>
This method calls on the Wrapper class to return all Light (type 0) and Heavy (type 1) routes from the MBTA API and stores them in ArrayList<Route> in the Core. This method is only called once by initialize() in the App class. It uses the JSON-simple library to parse the JSONObjects from the wrapper into an instance of the Route class (Model).

*parseStops()* <br>
Similar to parseRoutes(), this method uses the Wrapper class to pull all stops on Light and Heavy routes from the MBTA API. They are stored in a 2d array (ArrayList<ArrayList<Stop>>) in the Core class. Each column in the 2d array holds all stops for one route. While the route or its data is not saved in this 2d array, the indices of each array of stops corresponds to the index of the relevant route in the routes (ArrayList<Route>) variable returned from parseRoutes(). The getRouteIndex(Route r) method can be used to find the correct index. Each Stop instance also holds the route ID of the route it is on.

*addConnectingStop(Stop stop, Route route1)* <br>
As parseStops() runs, it calls this method each time a stop is found. It checks to see if any stop already exists with the same stop ID. If one is found, it means we have found a connecting stop. This stop and each route it is on are added to the connectingStops HashMap(String, ArrayList<Route>) variable. Each route is also updated with this stop and the route it connects to within the route's connectingStops variable, so that each route knows which other routes it's connected to and from which stop.

### Wrapper
This module deals solely with communicating with the API and is the only module that does so. Since all the API data is stored in the Core, this class is only called during initialization. The Wrapper class uses the HttpURLConnection to communicate with the MBTA API and returns JSONObjects. The Core classes then use the JSON-simple library to parse that data and store it. The main method in this class is *request(String url)*. The other two methods *getRoutes()* and *getString(String url)* construct strings to be appended to the base url for the request.

*request(String footer)* <br> 
This method is the only one that actually communicates with the API. The wrapper class has variables holding the base URL *"https://api-v3.mbta.com"* and my API Key. Using my API key and the given *footer* string, it opens a connection with the API. After making the GET request, it checks the response code to ensure there were no errors. It then uses a Scanner to read each line of the stream from the API and uses a JSONParser from JSON-simple to create a JSONObject which it returns.

*getRoutes()* <br>
This method sends the string *"/routes?filter%5Btype%5D=0%2C1"* to the request method. It asks the API to return all routes of type 0 (Light) and 1 (Heavy). This is hardcoded because these were the only route types that were needed for this program, but this string can be updated to request more types.

*getStops(String routeID)* <br>
This method creates the string *"/stops?include=route&filter%5Broute%5D=" + routeID* as the footer for the request. It gathers all the stops on the route given. This method is called once per route that was found from getRoutes().

### Model
***Model*** <br>
The model class holds the main functionality for the program and computes the data. It contains 3 methods, getRouteMostStops(), getRouteLeastStops(), and getDirections(). Each method does one computation and returns the result to the Core to be displayed to the user.

***Route*** <br>
This class represents an MBTA route. It holds the name and ID of the route which come directly from the API. While the API holds much more info about the route, this is all that is pulled. Because the data is held in memory, I limited what information was stored to only what was relevant for the current program. If more information is needed, the functionality can easily be extended in the wrapper class.
The class also holds a HashMap<Route, ArrayList<String>>. This is updated when Core.parseStops() is called during App:initialize(). It holds all routes in which this route connects to and which stop(s) connect them.

***Stop*** <br>
This class represents an MBTA stop. It holds the name and ID of the stop as well as the route that it is on. Similarly, to the route class, it was limited to information that was needed for this program but can be extended in the Wrapper class. If a stop is found twice, it is considered a connecting stop because it is on two routes. Each route will have a separate instance of the stop. Each stop instance will have the same name and ID, but different route IDs.


## Development
This program was built as a multi-module maven project. Each module has its own functionality, separate from other modules. The Wrapper module's function is to interact with the API and return the correct data to the other modules. The Model contains most of the main functionality of the program by analyzing and computing the data based on the available command line options. The Core acts as the controller mediating communication between itself, the user, the model, and the wrapper. Because of this, the Core has dependencies for the model and the wrapper in its pom.xml file.

## how to run
running 
options

## Questions
***Question 1*** <br>
> Write a program that retrieves data representing all, what we'll call > "subway" routes: "Light Rail" (type 0) and “Heavy Rail” (type 1). The > program should list their “long names” on the console.
> Partial example of long name output: Red Line, Blue Line, Orange Line...
> There are two ways to filter results for subway-only routes. Think about the > two options below and choose:
> 1. Download all results from https://api-v3.mbta.com/routes then filter locally
> 2. Rely on the server API (i.e., https://api-v3.mbta.com/routes?filter[type]=0,1) to filter before results
are received
> Please document your decision and your reasons for it.

The first command line option allows the user to see a list of all Light and Heavy Routes on the MBTA. I decided to go with the second option of having the API filter the results. The reason for this was efficiency. As there are 5 different types of routes, it would like be a large amount of data to download and then parse through.

***Question 2*** <br>
> Extend your program so it displays the following additional information.
> 1. The name of the subway route with the most stops as well as a count of its stops.
> 2. The name of the subway route with the fewest stops as well as a count of its stops.
> 3. A list of the stops that connect two or more subway routes along with the relevant route names for
each of those stops.

The second question mostly relies on analyzing the data that was received within the first question. This required some refactoring to ensure that the correct information was saved and easily accessible after being received from the API.

*2.1/2.2* <br>
Since every stop is saved within a 2d array in the Core, the methods for these questions simply loop through the outer array comparing each of the sizes for the inner arrays determining which is the largest and the smallest.

*2.3* <br>
As with the first two parts, most of the functionality for this question is set up during initialization. As stops are being parsed, their stop ID is added to a Hashmap. If their ID already exists in the map, the stop, and both routes are updated to hold this information. When the user asks to see this information, the program simply prints out the information saved within the Hashmap.

***Question 3*** <br>
> Extend your program again such that the user can provide any two stops on the subway routes you listed for question 1.
> List a rail route you could travel to get from one stop to the other. We will not evaluate your solution based upon the efficiency or cleverness of your route-finding solution. Pick a simple solution that answers the question. We will want you to understand and be able to explain how your algorithm performs.

This problem relied on the connecting stop functionality. The program reads in the two stops that were given by the user and then determines if they are connected through the saved arrays. If they are not, the program will find a route the both of these routes connect to.