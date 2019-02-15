package com.net;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Connector {
    private List<Request> requests;

    public Connector() {
        requests = new ArrayList<>();
    }

    public void add(Request request) {
        requests.add(request);
        CompletableFuture.runAsync(handleRequest());
    }

    private synchronized List<Request> getRequests() {
        return requests;
    }

    private Runnable handleRequest() {
        return () -> {
            Request r = getRequests().get(0);
            getRequests().remove(0);
            try {
                switch (r.getMethod()) {
                    case GET:
                        handleGetRequest(r);
                        break;
                    case POST:
                        handlePostRequest(r);
                        break;
                    case DELETE:
                        handleDeleteRequest(r);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private void handleGetRequest(Request r) throws IOException {
        URL url = new URL(r.getUrl());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int status = conn.getResponseCode();
        System.out.println(status);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        try {
            JSONArray array = new JSONArray(content.toString());
            r.getResponseListener().onResponse(array);
        } catch (JSONException e) {
            r.getErrorListener().onError(new Error(e));
        }
        in.close();
        conn.disconnect();

    }

    private void handlePostRequest(Request r) throws IOException {
        URL url = new URL(r.getUrl());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("POST");

        OutputStream os = conn.getOutputStream();
        os.write(r.getJsonObject().toString().getBytes(StandardCharsets.UTF_8));
        os.close();


        StringBuilder sb = new StringBuilder();
        int HttpResult = conn.getResponseCode();
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            System.out.println("" + sb.toString());
        } else {
            System.out.println(conn.getResponseMessage());
        }
        conn.disconnect();
    }

    private void handleDeleteRequest(Request r) throws IOException {
        URL url = new URL(r.getUrl());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");


        conn.disconnect();

    }
}
