package com.mbta;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/*
 * Class that communicates with MBTA API and recieves and returns (route and stop) data
 *  * baseURL (String) - url of MBTA API with no request information
 *  * apiKey (String) - Katie Welch's API key for MBTA API
 */
public class Wrapper {
    private String baseURL;
    private String apiKey;

    public Wrapper() {
        baseURL = "https://api-v3.mbta.com";
        apiKey = "b925076dae244748b86d02f4e02baac8";
    }

    /*
     * pulls all Light (type 0) and Heavy (Type 1) routes on MBTA
     * is called from Core which will hold all route data
     * should only be called once
     */
    public JSONObject getRoutes() {
        String routeFooter = "/routes?filter%5Btype%5D=0%2C1";
        return request(routeFooter);
    }

    /*
     * pulls all stops on Light (type 0) and Heavy (Type 1) routes on MBTA
     * is called from Core which will hold all route data
     * should only be called once
     */
    public JSONObject getStops(String routeID) {
        String stopFooter = "/stops?include=route&filter%5Broute%5D=" + routeID;
        return request(stopFooter);
    }

    /*
     * method that makes requests to API
     *  * url (String) is string that should be appended to baseURL and specifies GET request and filters
     */
    public JSONObject request(String footer) {
        JSONObject dataObj = null;

        try {
            URL urlObj = new URL(baseURL + footer);

            //api request specifications
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestProperty("Authorization", apiKey);
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();

            //code for too many requests, try again
            while (responsecode == 429) {
                conn.connect();
                responsecode = conn.getResponseCode();
            }

            if (responsecode != 200) { //failure code
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {
                String inline = "";
                Scanner sc = new Scanner(urlObj.openStream());

                //copy all JSON data from url
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }

                sc.close();

                JSONParser parse = new JSONParser();
                dataObj = (JSONObject) parse.parse(inline);
                return dataObj;
            }
        } catch (Exception e) {
            //If class cannot communicate with API
            e.printStackTrace();
            throw new RuntimeException("Could not communicate with MBTA API, please try again");
        }
    }  
}
