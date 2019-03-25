package com.ruben.clientandroid.Api.volley;

import com.android.volley.toolbox.JsonRequest;

public interface VolleyRequest {
    VolleyCallback GetCallback();

    JsonRequest DoRequest();
}
