package com.example.testapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class ParsingData {

    private HashMap<Integer, ArrayList<String>> data = new HashMap<Integer, ArrayList<String>>();
    private ArrayList<Integer> dataKeys;

    //
    // Constructor Creates Connection To Read Remote JSON file
    // HashMap Is Created To Group Values By ListId
    //
    public ParsingData() {
        try {
            URL url = new URL("https://fetch-hiring.s3.amazonaws.com/hiring.json");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader streamReader = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);

            JSONObject obj;
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                obj = new JSONObject(line);
                populateHashMap(obj.getInt("listId"), obj.getString("name"));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        //
        // Keys and Values Are Sorted
        //
        dataKeys = new ArrayList<Integer>(data.keySet());
        for(int i=0; i<dataKeys.size(); i++){
            Collections.sort(data.get(dataKeys.get(i)));
        }

    }

    //
    // Values Are Grouped By Same Keys
    // NULL And Empty Values Are Eliminated
    //
    private void populateHashMap(int listId, String name){
        if(name != null && !name.isEmpty() && !name.equals("null")){
            if(data.containsKey(listId)){
                data.get(listId).add(name);
            }else{
                ArrayList<String> arr = new ArrayList<String>();
                arr.add(name);
                data.put(listId, arr);
            }
        }
    }

    public HashMap<Integer, ArrayList<String>> getData(){
        return data;
    }

    public ArrayList getDataKeys(){
        return dataKeys;
    }

}
