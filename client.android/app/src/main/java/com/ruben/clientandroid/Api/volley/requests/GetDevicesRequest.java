package com.ruben.clientandroid.Api.volley.requests;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.ruben.clientandroid.Api.volley.VolleyCallback;
import com.ruben.clientandroid.Api.volley.VolleyRequest;
import com.ruben.clientandroid.Models.Device;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class GetDevicesRequest implements VolleyRequest {

    private VolleyCallback<ArrayList<Device>> callback;
    private String url;

    public GetDevicesRequest(VolleyCallback<ArrayList<Device>> callback, String url) {
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
                        ArrayList<Device> devices = new ArrayList<>();
                        JSONArray array = response.getJSONArray("devices");

                        for (int i = 0; i < array.length(); i++)
                            devices.add(Device.fromJson(array.getJSONObject(i).toString()));

                        this.callback.OnResponse(devices);

                    } catch (JSONException e) {
                        this.callback.OnError(new Error(e.getMessage()));
                    }

                },
                error -> {
                    this.callback.OnError(new Error(error.getMessage()));
                }
        );
    }
}
