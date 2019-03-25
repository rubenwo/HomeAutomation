package com.ruben.clientandroid.Models;

import java.util.ArrayList;

import static com.ruben.clientandroid.Contants.GSON;

public class Room {
    private String identifier;
    private String name;
    private ArrayList<Device> devices;

    public static Room fromJson(String json) {
        return GSON.fromJson(json, Room.class);
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    @Override
    public String toString() {
        return "Room{" +
                "identifier='" + identifier + '\'' +
                ", name='" + name + '\'' +
                ", devices=" + devices +
                '}';
    }
}