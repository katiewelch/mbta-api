package com.mbta;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Wrapper {
 
    private String baseURL;
    private String apiKey;

    public Wrapper() {
        baseURL = "https://api-v3.mbta.com";
        apiKey = "b925076dae244748b86d02f4e02baac8";
    }

    public JSONObject getRoutes() {
        String routeFooter = "/routes?filter%5Btype%5D=0%2C1";
        return request(routeFooter);
    }

    public JSONObject getStops(String routeID) {
        String stopFooter = "/stops?include=route&filter%5Broute%5D=" + routeID;
        return request(stopFooter);
    }

    public JSONObject request(String url) {
        JSONObject dataObj = null;
        try {
            URL urlObj = new URL(baseURL + url);

            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestProperty("Authorization", apiKey);
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();

            while (responsecode == 429) {
                conn.connect();
                responsecode = conn.getResponseCode();
            }

            if (responsecode != 200) {
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
            e.printStackTrace();
        }
        //TODO: should never get here, should this be null?
        if (dataObj == null)
            throw new RuntimeException("oops");
        return dataObj;
    }   
}
