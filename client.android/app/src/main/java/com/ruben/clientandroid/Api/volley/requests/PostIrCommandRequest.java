package com.ruben.clientandroid.Api.volley.requests;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.ruben.clientandroid.Api.volley.VolleyCallback;
import com.ruben.clientandroid.Api.volley.VolleyRequest;

public class PostIrCommandRequest implements VolleyRequest {
    private VolleyCallback callback;
    private String url;

    public PostIrCommandRequest(VolleyCallback callback, String url) {
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
                Request.Method.POST,
                this.url,
                null,
                response -> {
                    this.callback.OnResponse(response);
                },
                error -> {
                    this.callback.OnError(new Error(error.getMessage()));
                }
        );
    }
}
