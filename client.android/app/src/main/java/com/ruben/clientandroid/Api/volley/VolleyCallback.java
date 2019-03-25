package com.ruben.clientandroid.Api.volley;

import org.json.JSONObject;

public interface VolleyCallback {
    void OnResponse(JSONObject data);

    void OnError(Error error);
}
