# MBTA API
TODO
general organzing
    same fonts boldness etc for classes, methods, variables
make diagram of classes
move wrapper section above core
spell check in different app


## overview
This program communicates with the [MBTA's API](https://api-v3.mbta.com/docs/swagger/index.html#/) to parse data and create a command line interface where users can get information regarding routes and stops.

## Project Setup
### core
The core package of this project acts as the controller of the program. It contains the methods that run on startup and initialize the program as well as methods that mediate communication with the user. It also holds instances of the Model and Wrapper classes and calls them accordingly based on the user's input.

**App**
    The App class contains the main method that runs on startup ANOTHER WORD?as well as the two other methods that run on startup and comminicate with the user, respecitvely: initialize() and decode(String s, Model m).

*main( String[] args)*
    The main method creates instances of the Model and Core classes, prints a welcome message and options to the user, and initializes the data in the program. It contains a while loop that is constantly scans for user input on the command line, and send the input to the decode(String s, Model m) method to be properly dealt with. NEW WORD

*initialize()*
    This method calls parseRoutes() and parseStops() from the Core class. It is only called once at the beginning of the program and pulls all the route and stop data from the MBTA API and stores it in arrays within the Core class. The was done to limit the number of API calls throughout the program and increase the efficiecy each time the user makes a command line request. While it does make the start up time of the program longer and requires the core instance to hold the data within memory, neither of these are large enough to have an affect on the program.

*decode(String s, Model m)*
    The deode method deals with all command line input by the from the user. The command line options are listed below and can be shown by typing "o" in the command line at any time.

> Please select an option:
> 1: List subway routes
> 2.1: Subway route with most stops
> 2.2: Subway route with least stops
> 2.3: List of stops connecting 2 or more routes
> 3: Directions

    The method runs a switch loop that calls the correct method in either the core (fetch data) or the model (compute data) and prints out the answer to the command line. If an incorrect option is entered, the user will get an error message and can input a new option code.

**Core**
    The core and app classes work together to act as a controller for the program. The app works as a mediator between the user and the program while the core acts as the main controller for the backend mediating communication between the user, data, wrapper (API), and the model.

methods

*parseRoutes()*
    This method calls on the Wrapper class to return all Light (type 0) and Heavy (type 1) routes from the MBTA API and stores them in ArrayList<Route> in the core. This method is only called once by the initialize method in the App class. It uses the JSON-simple library to parse the JSONObjects from the wrapper into an instance of the Route class.

*parseStops()*
    Similar to parseRoutes(), this method uses the Wrapper class to pull all stops on Light and Heavy routes from the MBTA API. They are stored in a 2d array (ArrayList<ArrayList<Stop>>) in the core class. This method also uses JSON-simple library to parse the objects returned from the wrapper into instance of the Stop class. Each column in the 2d array hold all stops for one route. While the route is not saved in this 2d array, the indices of each array of stops, corresponds to the index of the relevant route in the routes (ArrayList<Route>) variable returned from parseRoutes(). The getRouteIndex(Route r) method can be used to find the correct index. Each Stop instance also holds the route ID of the route it is on.

*addConnectingStop(Stop stop, Route route1)*
    As parseStops() runs, it call this method each time a stop is found. It checks to see if any stop already exists with the same stop ID. If one is found, it means we have found a connecting stop, a stop that connects two routes. This stop and each route it is on are added to the connectingStops HashMap(Stop, ArrayList<Route>) variable. Each route is also updated with this stop and the route it connects to within the route's connectingStops variable, so that each route knows which other routes it's connected to and from which stop.

### model
***Model***
***Route***
    This class represents an MBTA route. It holds the name and ID of the route which come directly from the API. While the API holds much more info about the route, this is all that is pulled. Because the data is held in memory, I limited what information was stored to only what was relevant for the current program. If more information is need, the functionality can easily be extended in the wrapper class.
    The class also holds a HashMap<Route, ArrayList<String>>. This is updated when Core.parseStops() is called during App:initialize(). It holds all routes in which this route connects to and which stop(s) connect them.

***Stop***
    This class represents an MBTA stop. It holds the name and ID of the stop as well as the route that it is on. Similarly, to the route class, it was limited to information that was need for this program but can be extended in the Wrapper class. If a stop is found twice, it is considered a connecting stop because it is on two routes. Each route will have a separate instance of the stop. Each stop instance will have the same name and ID, but differnt route IDs. 

### wrapper
    This module deals solely with communicating with the API and is the only module that does so. Since all the API data is stored in the core, this class is only called during initialization. The Wrapper class uses the HttpURLConnection to communicate with the MBTA API and returns JSONObjects. The Core classes then uses the JSON-simple library to parse that data and store it. The main method in this class is *request(String url)*. The other two methods *getRoutes()* and *getString(String url)* construct strings to be appended to the base url for the request.

*request(String footer)*
    This method is the only one that actually communicates with the API. The wrapper class has variables holding the base URL *"https://api-v3.mbta.com"* and my API Key. Using my API key and the given *footer* string, it opens a connection with the API. After making the GET request, it checks the repsonse code to ensure there were no errors. It then uses a Scanner to read each line of the stream from the API and uses a JSONParser from JSON-simple to create a JSONObject which it returns.

*getRoutes()*
    This method sends the string *"/routes?filter%5Btype%5D=0%2C1"* to the request method. It asks the API to return all routes of type 0 (Light) and 1 (Heavy). This is hardcoded because these were the only route types that were need for this program, but this string can be updated to request more types.

*getStops(String routeID)*
    This method creates the string *"/stops?include=route&filter%5Broute%5D=" + routeID* as the footer for the request. It gathers all the stops on the route given. This method is called once per route that was found from getRoutes().


## Development
This program was built as a multi-module maven project. Each module has its own functionality, separate from other modules. The Wrapper module's function is to interact with the API and return the correct data to the other modules. The Model conatins most of the main functionality of the program by analyzing and computing the data based on the available command line options. The Core acts as the controller mediating communication between itself, the user, the model, and the wrapper. Because of this, the core has dependencies for the model and the wrapper in it's pom.xml file.

### Build
### Tests
    Each module has it's own test folder with contains unit tests for it's classes. The model and core test files also contain tests that look at the larger functionality of the program.

### Libraries
    HTTP Response - Swagger UI

## how to run
running 
options

## questions
> Question 1
> Write a program that retrieves data representing all, what we'll call > "subway" routes: "Light Rail" (type 0) and “Heavy Rail” (type 1). The > program should list their “long names” on the console.
> Partial example of long name output: Red Line, Blue Line, Orange Line...
> There are two ways to filter results for subway-only routes. Think about the > two options below and choose:
> 1. Download all results from https://api-v3.mbta.com/routes then filter locally
> 2. Rely on the server API (i.e., https://api-v3.mbta.com/routes?filter[type]=0,1) to filter before results
are received
> Please document your decision and your reasons for it.

    The first command line option allows the user to see a list of all Light and Heavy Routes on the MBTA. I decided to go with the second option of having the API filter the results. The reason for this was efficiency. As there are 5 different types of routes, it would like be a large amount of data to download and then parse through.

2
The second question mostly relying on analyzing the data that was recieved within the first question. This required some refactoring to ensure that the ocrrect infomration was saved and easily accessible after being received from the API.

    2.1/2.2
    Since every stop is saved within a 2d array in the Core, the methods for these questions simply loop through the outer array comparing each of the sizes for the inner arrays determining which is the largest and the smallest.

    2.3
    As with the first two parts, most of the functionality for this question is set up during initialization. As stops are being parsed, their stop ID is added to a Hashmap. If their ID already exists in the map, the stop, and both routes are updated to hold this information. When the user asks to see this information, the proigram simply prints out the information saved within the Hashmap.

3
This problem relied on the connecting stop functionality. The program reads in the two stops that were given by the user and then determines if they are connected through the saved arrays. If they are not, the program will find a route the both of these routes connect to.