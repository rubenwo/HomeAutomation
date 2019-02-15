package com.net;

import org.json.JSONObject;

public class Request {
    private String url;
    private RequestMethod method;
    private JSONObject jsonObject;
    private ResponseListener responseListener;
    private ErrorListener errorListener;

    public Request(String url, RequestMethod method, JSONObject jsonObject, ResponseListener responseListener, ErrorListener errorListener) {
        this.url = url;
        this.method = method;
        this.jsonObject = jsonObject;
        this.responseListener = responseListener;
        this.errorListener = errorListener;
    }

    public String getUrl() {
        return url;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public ResponseListener getResponseListener() {
        return responseListener;
    }

    public ErrorListener getErrorListener() {
        return errorListener;
    }
}
