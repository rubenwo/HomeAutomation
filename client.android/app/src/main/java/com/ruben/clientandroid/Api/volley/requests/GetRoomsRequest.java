package com.ruben.clientandroid.Api.volley.requests;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.ruben.clientandroid.Api.volley.VolleyCallback;
import com.ruben.clientandroid.Api.volley.VolleyRequest;
import com.ruben.clientandroid.Models.Room;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class GetRoomsRequest implements VolleyRequest {
    private VolleyCallback<ArrayList<Room>> callback;
    private String url;

    public GetRoomsRequest(VolleyCallback<ArrayList<Room>> callback, String url) {
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
                        ArrayList<Room> rooms = new ArrayList<>();
                        JSONArray array = response.getJSONArray("rooms");

                        for (int i = 0; i < array.length(); i++)
                            rooms.add(Room.fromJson(array.getJSONObject(i).toString()));

                        this.callback.OnResponse(rooms);

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
