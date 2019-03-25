package com.ruben.clientandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ruben.clientandroid.Adapters.DeviceRecyclerAdapter;
import com.ruben.clientandroid.Api.eventbus.EventBusService;
import com.ruben.clientandroid.Api.volley.VolleyCallback;
import com.ruben.clientandroid.Api.volley.VolleyService;
import com.ruben.clientandroid.Api.volley.requests.GetDevicesRequest;
import com.ruben.clientandroid.Models.Device;
import com.ruben.clientandroid.Models.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private VolleyService volleyService;
    private EventBusService eventBusService;

    private DeviceRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Device> devices;
    private ArrayList<Room> rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        devices = new ArrayList<>();


        mRecyclerView = findViewById(R.id.device_recyclerview);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DeviceRecyclerAdapter(getApplicationContext(), devices);
        mRecyclerView.setAdapter(mAdapter);


        volleyService = VolleyService.getInstance(getApplication());
        try {
            URI eventServerUri = new URI("ws://10.0.2.2/event-bus/sub");
            eventBusService = EventBusService.getInstance(eventServerUri, event -> {
                Log.d("MAINACTIVITY_TAG", "event: Got an event");
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (eventBusService != null) {
            eventBusService.connect();
        } else {
            Log.d("MAINACTIVITY_TAG", "onCreate: can't connect to event-bus service");
        }
        volleyService.doRequest(new GetDevicesRequest(new VolleyCallback() {
            @Override
            public void OnResponse(JSONObject data) {
                Log.d("MAINACTIVITY_TAG", "OnResponse: " + data.toString());
                try {
                    JSONArray array = data.getJSONArray("devices");
                    devices.clear();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        devices.add(Device.fromJson(obj.toString()));
                    }
                    mAdapter.notifyDataSetChanged();

                    for (Device device : devices)
                        Log.d("MAINACTIVITY_TAG", "OnParsing: " + device.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(Error error) {
                Log.d("MAINACTIVITY_TAG", "OnError: " + error.getMessage());
            }
        }, Contants.API_GATEWAY + "/registry/devices"));
    }
}
