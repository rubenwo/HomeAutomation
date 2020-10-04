package com.ruben.clientandroid.Models;

import org.json.JSONObject;

import static com.ruben.clientandroid.Contants.GSON;

public class Event {
    private String identifier;
    private String name;
    private String type;
    private JSONObject data;

    public static Event fromJson(String json) {
        return GSON.fromJson(json, Event.class);
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public JSONObject getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Event{" +
                "identifier='" + identifier + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", data=" + data +
                '}';
    }
}
