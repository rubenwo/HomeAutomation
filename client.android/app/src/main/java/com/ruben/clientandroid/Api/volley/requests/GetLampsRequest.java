package com.ruben.clientandroid.Api.volley.requests;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.ruben.clientandroid.Api.volley.VolleyCallback;
import com.ruben.clientandroid.Api.volley.VolleyRequest;
import com.ruben.clientandroid.Models.HueLamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetLampsRequest implements VolleyRequest {
    private VolleyCallback<ArrayList<HueLamp>> callback;
    private String url;

    public GetLampsRequest(VolleyCallback<ArrayList<HueLamp>> callback, String url) {
        this.callback = callback;
        this.url = url;
    }

    @Override
    public VolleyCallback GetCallback() {
        return callback;
    }

    @Override
    public JsonRequest DoRequest() {
        return new JsonObjectRequest(
                Request.Method.GET,
                this.url,
                null,
                response -> {
                    try {
                        Log.e("TRY", "TRYTRY");
                        ArrayList<HueLamp> lamps = new ArrayList<>();
                        JSONArray array = response.getJSONArray("lights");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject light = array.getJSONObject(i);
                            JSONObject state = light.getJSONObject("state");

                            HueLamp lamp = new HueLamp(Integer.valueOf(light.getString("id")),
                                    light.getString("name"),
                                    state.getBoolean("on"),
                                    state.getInt("bri"),
                                    state.getInt("hue"),
                                    state.getInt("sat"),
                                    state.getString("colormode"),
                                    state.getBoolean("reachable"));
                            Log.e("NEW LAMP:", "Lamp: " + lamp.toString());
                            lamps.add(lamp);
                        }

                        this.callback.OnResponse(lamps);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        this.callback.OnError(new Error(e.getMessage()));
                    }
                },
                error -> {
                    this.callback.OnError(new Error(error.getMessage()));
                }
        );
    }
}
