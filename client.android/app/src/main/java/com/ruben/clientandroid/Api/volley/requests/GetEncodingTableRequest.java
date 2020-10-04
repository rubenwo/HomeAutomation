package com.ruben.clientandroid.Api.volley.requests;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.ruben.clientandroid.Api.volley.VolleyCallback;
import com.ruben.clientandroid.Api.volley.VolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class GetEncodingTableRequest implements VolleyRequest {
    private VolleyCallback<Map<String, String[]>> callback;
    private String url;

    public GetEncodingTableRequest(VolleyCallback<Map<String, String[]>> callback, String url) {
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
                        Map<String, String[]> encodings = new HashMap<>();

                        JSONArray array = response.getJSONArray("encodings");

                        // for (int i = 0; i < array.length(); i++) {
                        //    encodings.add(array.getString(i));
                        // }

                        this.callback.OnResponse(encodings);
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
