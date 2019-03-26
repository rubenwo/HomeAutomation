package com.ruben.clientandroid.Api.volley.requests;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.ruben.clientandroid.Api.volley.VolleyCallback;
import com.ruben.clientandroid.Api.volley.VolleyRequest;
import com.ruben.clientandroid.Models.Lamp;

import java.util.ArrayList;

public class GetLampsRequest implements VolleyRequest {
    private VolleyCallback<ArrayList<Lamp>> callback;
    private String url;

    public GetLampsRequest(VolleyCallback<ArrayList<Lamp>> callback, String url) {
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

                },
                error -> {
                    this.callback.OnError(new Error(error.getMessage()));
                }
        );
    }
}
