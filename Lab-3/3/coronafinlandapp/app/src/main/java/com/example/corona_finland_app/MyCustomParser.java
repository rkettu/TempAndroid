package com.example.corona_finland_app;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyCustomParser {

    public static List<MyCustomCorona> parse (JSONObject jObj)  {
        List<MyCustomCorona> resultsList = new ArrayList<>();

        Log.d("HEREWEARE", "once again");


        try {
            JSONArray hospitalised = jObj.getJSONArray("hospitalised");
            for(int i = 0; i < hospitalised.length(); i++)
            {
                JSONObject j = (JSONObject) hospitalised.get(i);
                String date = DateParser.parse(j.getString("date"));
                MyCustomCorona corona = new MyCustomCorona(date,j.getString("area"),
                        j.getInt("totalHospitalised"),j.getInt("inWard"),
                        j.getInt("inIcu"),j.getInt("dead"));
                resultsList.add(corona);
            }
        } catch(Exception e) {
            Log.d(MainActivity.TAG, "Exception: " + e.toString());
            e.printStackTrace();
        }

        return resultsList;
    }
}
