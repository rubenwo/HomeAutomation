package com.ruben.clientandroid.Models;

import android.util.Log;

import com.ruben.clientandroid.Api.volley.VolleyCallback;
import com.ruben.clientandroid.Api.volley.VolleyService;
import com.ruben.clientandroid.Api.volley.requests.GetEncodingTableRequest;
import com.ruben.clientandroid.Api.volley.requests.PostIrCommandRequest;
import com.ruben.clientandroid.Contants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IrController {
    private Device device;
    private Map<String, String[]> encodingTables;
    private VolleyService service;

    public IrController(Device device) {
        this.device = device;
        service = VolleyService.getInstance(null);
        encodingTables = new HashMap<>();
        getEncodingTable();
    }

    public void sendCommand(String command, String device) {
        if (!exists(encodingTables.get(device), command)) {
            return;
        }
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("command", command);
            obj.put("device", device);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        service.doRequest(new PostIrCommandRequest(new VolleyCallback<String>() {
            @Override
            public void OnResponse(String data) {
                Log.d("IR_CONTOLLER_TAG", "OnResponse: " + data);
            }

            @Override
            public void OnError(Error error) {
                Log.d("IR_CONTOLLER_TAG", "OnError: " + error.getMessage());
            }
        },
                Contants.API_GATEWAY + "/ir_controller/command",
                obj));
    }

    private boolean exists(String[] data, String key) {
        if (data == null) {
            return false;
        }
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals(key)) {
                return true;
            }
        }
        return false;
    }

    private void getEncodingTable() {
        service.doRequest(new GetEncodingTableRequest(new VolleyCallback<Map<String, String[]>>() {
            @Override
            public void OnResponse(Map<String, String[]> data) {
                encodingTables.clear();
                encodingTables.putAll(data);
            }

            @Override
            public void OnError(Error error) {

            }
        }, Contants.API_GATEWAY + "/ir_controller/encoding_tables"));
    }
}
