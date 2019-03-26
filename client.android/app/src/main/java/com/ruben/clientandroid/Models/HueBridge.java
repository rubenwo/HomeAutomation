package com.ruben.clientandroid.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class HueBridge implements Parcelable {

    public int id;
    public String name;
    public String ip;
    public String token;


    public HueBridge(int id, String name, String ip, String token) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.token = token;
    }

    protected HueBridge(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ip = in.readString();
        token = in.readString();
    }

    public static final Creator<HueBridge> CREATOR = new Creator<HueBridge>() {
        @Override
        public HueBridge createFromParcel(Parcel in) {
            return new HueBridge(in);
        }

        @Override
        public HueBridge[] newArray(int size) {
            return new HueBridge[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public String getToken() {
        return token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(ip);
        parcel.writeString(token);
    }
}
