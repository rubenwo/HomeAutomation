package com.ruben.clientandroid.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class HueLamp implements Parcelable {

    public int id;
    public String name;
    public boolean on;
    public int brightness;
    public int hue;
    public int saturation;
    public String colorMode;
    public boolean reachable;

    //brightness hue saturation

    public HueLamp(int id, String name, boolean on, int bri, int hue, int sat, String colorMode, boolean reachable)
    {
        this.name = name;
        this.on = on;
        this.brightness = bri;
        this.hue = hue;
        this.saturation = sat;
        this.colorMode = colorMode;
        this.reachable = reachable;
        this.id = id;
    }

    protected HueLamp(Parcel in) {
        name = in.readString();
        on = in.readByte() != 0;
        brightness = in.readInt();
        hue = in.readInt();
        saturation = in.readInt();
        colorMode = in.readString();
        reachable = in.readByte() != 0;
        id = in.readInt();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (on ? 1 : 0));
        dest.writeInt(brightness);
        dest.writeInt(hue);
        dest.writeInt(saturation);
        dest.writeString(colorMode);
        dest.writeByte((byte) (reachable ? 1:0));
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HueLamp> CREATOR = new Creator<HueLamp>() {
        @Override
        public HueLamp createFromParcel(Parcel in) {
            return new HueLamp(in);
        }

        @Override
        public HueLamp[] newArray(int size) {
            return new HueLamp[size];
        }
    };

    @Override
    public String toString() {
        return "HueLamp{" +
                "name='" + name + '\'' +
                ", on=" + on +
                ", brightness=" + brightness +
                ", hue=" + hue +
                ", saturation=" + saturation +
                ", colorMode='" + colorMode + '\'' +
                ", reachable=" + reachable +
                '}';
    }

    //-------//
    //Getters//
    //-------//
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public String getColorMode() {
        return colorMode;
    }

    public boolean isOn() {
        return on;
    }

    public boolean isReachable() {
        return reachable;
    }


    //-------//
    //Setters//
    //-------//
    public void setName(String name) {
        this.name = name;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public void setColorMode(String colorMode) {
        this.colorMode = colorMode;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }


    public void setId(int id) {
        this.id = id;
    }
    //------//
}