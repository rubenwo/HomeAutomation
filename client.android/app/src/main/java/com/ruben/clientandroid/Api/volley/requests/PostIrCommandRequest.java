package com.ruben.clientandroid.Api.volley.requests;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.ruben.clientandroid.Api.volley.VolleyCallback;
import com.ruben.clientandroid.Api.volley.VolleyRequest;

import org.json.JSONObject;

public class PostIrCommandRequest implements VolleyRequest {
    private VolleyCallback<String> callback;
    private JSONObject jsonObject;
    private String url;

    public PostIrCommandRequest(VolleyCallback<String> callback, String url, JSONObject jsonObject) {
        this.callback = callback;
        this.url = url;
        this.jsonObject = jsonObject;
    }

    @Override
    public VolleyCallback GetCallback() {
        return callback;
    }

    @Override
    public JsonRequest DoRequest() {
        return new JsonObjectRequest(
                Request.Method.POST,
                this.url,
                jsonObject,
                response -> {
                    this.callback.OnResponse(response.toString());
                },
                error -> {
                    this.callback.OnError(new Error(error.getMessage()));
                }
        );
    }
}
