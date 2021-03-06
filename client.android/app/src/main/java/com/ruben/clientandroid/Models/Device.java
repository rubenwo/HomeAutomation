package com.ruben.clientandroid.Models;

import com.ruben.clientandroid.Contants;

import java.io.Serializable;

public class Device implements Serializable {
    private String identifier;
    private String name;
    private String device_type;
    private String controller_name;
    private String ip_address;
    private String room_identifier;

    public Device(String identifier, String name, String device_type, String controller_name, String ip_address, String room_identifier) {
        this.identifier = identifier;
        this.name = name;
        this.device_type = device_type;
        this.controller_name = controller_name;
        this.ip_address = ip_address;
        this.room_identifier = room_identifier;
    }

    public static Device fromJson(String json) {
        return Contants.GSON.fromJson(json, Device.class);
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getDevice_type() {
        return device_type;
    }

    public String getController_name() {
        return controller_name;
    }

    public String getIp_address() {
        return ip_address;
    }

    public String getRoom_identifier() {
        return room_identifier;
    }

    @Override
    public String toString() {
        return "Device{" +
                "identifier='" + identifier + '\'' +
                ", name='" + name + '\'' +
                ", device_type='" + device_type + '\'' +
                ", controller_name='" + controller_name + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", room_identifier='" + room_identifier + '\'' +
                '}';
    }
}
