package com.ruben.clientandroid.Models;

public class Lamp {
    private String id;
    private String name;
    private boolean isOn;
    private int brightness;
    private int hue;
    private int saturation;

    public Lamp(String id, String name, boolean isOn, int brightness, int hue, int saturation) {
        this.id = id;
        this.name = name;
        this.isOn = isOn;
        this.brightness = brightness;
        this.hue = hue;
        this.saturation = saturation;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isOn() {
        return isOn;
    }

    public int getBrightness() {
        return brightness;
    }

    public int getHue() {
        return hue;
    }

    public int getSaturation() {
        return saturation;
    }

    @Override
    public String toString() {
        return "Lamp{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isOn=" + isOn +
                ", brightness=" + brightness +
                ", hue=" + hue +
                ", saturation=" + saturation +
                '}';
    }
}
