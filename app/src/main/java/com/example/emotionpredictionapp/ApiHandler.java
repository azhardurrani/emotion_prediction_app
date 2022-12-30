package com.example.emotionpredictionapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiHandler {

    static String baseURL = "http://192.168.1.114:5000";
    JSONObject resp =null;
    boolean waiting=true;

    public JSONObject postReuquest(final Context context, String reqURL , JSONObject postData){

        RequestQueue requestQueue = Volley.newRequestQueue(context);

//        JSONObject postData = new JSONObject();
//        try {
//
//
//            JSONArray jsonArray = new JSONArray(shortAudioData);
//            postData.put("audio",jsonArray);
//
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.d("VOLLEY", e.getMessage());
//            Toast.makeText(this, "Error in extracting raw audio frequency", Toast.LENGTH_SHORT).show();
//        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, baseURL+reqURL, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println("VOLLEY: "+response.toString());
                resp = response;


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
                Toast.makeText(context, "Connection Error: \n"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("VOLLEY"+ error.getMessage());
            }
        });

        requestQueue.add(jsonObjectRequest);


        return resp;
    }
}
