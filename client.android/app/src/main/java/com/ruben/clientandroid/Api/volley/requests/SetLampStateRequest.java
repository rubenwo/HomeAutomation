package com.ruben.clientandroid.Api.volley.requests;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.ruben.clientandroid.Api.volley.VolleyCallback;
import com.ruben.clientandroid.Api.volley.VolleyRequest;
import com.ruben.clientandroid.Models.HueLamp;

import org.json.JSONException;
import org.json.JSONObject;

public class SetLampStateRequest implements VolleyRequest {
    private VolleyCallback<String> callback;
    private HueLamp lamp;
    private String url;

    public SetLampStateRequest(VolleyCallback<String> callback, HueLamp lamp, String url) {
        this.callback = callback;
        this.lamp = lamp;
        this.url = url;
    }

    @Override
    public VolleyCallback GetCallback() {
        return callback;
    }

    @Override
    public JsonRequest DoRequest() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("on", lamp.on);
            obj.put("bri", lamp.brightness);
            obj.put("hue", lamp.hue);
            obj.put("sat", lamp.saturation);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new JsonObjectRequest(
                Request.Method.PUT,
                this.url,
                obj,
                response -> {
                    try {
                        this.callback.OnResponse(response.getString("description"));
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