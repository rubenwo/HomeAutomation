package com.ruben.clientandroid.Api.eventbus;

import android.util.Log;

import com.ruben.clientandroid.Models.Event;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class EventBusService extends WebSocketClient {
    private static EventBusService instance = null;
    private EventBusCallback callback;

    private EventBusService(URI serverUri, EventBusCallback callback) {
        super(serverUri);
        this.callback = callback;
    }

    public static EventBusService getInstance(URI serverUri, EventBusCallback callback) {
        if (instance == null)
            instance = new EventBusService(serverUri, callback);
        return instance;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("EVENT_TAG", "onOpen: " + handshakedata.getHttpStatusMessage());

    }

    @Override
    public void onMessage(String message) {
        this.callback.OnEvent(new Event());
        Log.d("EVENT_TAG", "onMessage: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d("EVENT_TAG", "onClose: " + reason);

    }

    @Override
    public void onError(Exception ex) {
        Log.d("EVENT_TAG", "onError: " + ex.getMessage());

    }
}
