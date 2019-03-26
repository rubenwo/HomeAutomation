package com.ruben.clientandroid.Api.volley.requests;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.ruben.clientandroid.Api.volley.VolleyCallback;
import com.ruben.clientandroid.Api.volley.VolleyRequest;
import com.ruben.clientandroid.Models.HueLamp;

import java.util.ArrayList;

public class SetLampStateRequest implements VolleyRequest {
    private VolleyCallback<HueLamp> callback;
    private String url;

    public SetLampStateRequest(VolleyCallback<HueLamp> callback, String url) {
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
                Request.Method.PUT,
                this.url,
                null, //hier moet een body komen?
                response -> {

                },
                error -> {
                    this.callback.OnError(new Error(error.getMessage()));
                }
        );
    }
}