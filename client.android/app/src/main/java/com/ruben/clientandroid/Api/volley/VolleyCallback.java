package com.ruben.clientandroid.Api.volley;

public interface VolleyCallback<T> {
    void OnResponse(T data);

    void OnError(Error error);
}
