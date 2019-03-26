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
import com.ruben.clientandroid.Api.volley.requests.GetRoomsRequest;
import com.ruben.clientandroid.Models.Device;
import com.ruben.clientandroid.Models.HueBridge;
import com.ruben.clientandroid.Models.Room;
import com.ruben.clientandroid.Services.NotificationService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private VolleyService volleyService;
    private EventBusService eventBusService;
    private NotificationService notificationService;

    private DeviceRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Device> devices;
    private ArrayList<Room> rooms;

    private ArrayList<HueBridge> bridges;
    public static final String BRIDGE_URL = "bridgeURL";
    public static final String LAMP_URL = "lampURL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        devices = new ArrayList<>();
        rooms = new ArrayList<>();

        mRecyclerView = findViewById(R.id.device_recyclerview);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DeviceRecyclerAdapter(getApplicationContext(), devices);
        mRecyclerView.setAdapter(mAdapter);
        
        volleyService = VolleyService.getInstance(getApplication());
        notificationService = NotificationService.getInstance(getApplication(), this);
        try {
            URI eventServerUri = new URI("ws://10.0.2.2/event-bus/sub");
            eventBusService = EventBusService.getInstance(eventServerUri, event -> {
                Log.d("MAINACTIVITY_TAG", "event: " + event.toString());
                notificationService.sendNotification(event);

            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (eventBusService != null) {
            eventBusService.connect();
            eventBusService.setConnectionLostTimeout(0);
        } else {
            Log.d("MAINACTIVITY_TAG", "onCreate: can't connect to event-bus service");
        }
        volleyService.doRequest(new GetDevicesRequest(new VolleyCallback<ArrayList<Device>>() {
            @Override
            public void OnResponse(ArrayList<Device> data) {
                Log.d("MAINACTIVITY_TAG", "OnResponse: " + data.toString());

                devices.clear();
                devices.addAll(data);
                mAdapter.notifyDataSetChanged();

                for (Device device : devices)
                    Log.d("MAINACTIVITY_TAG", "OnParsing: " + device.toString());
            }

            @Override
            public void OnError(Error error) {
                Log.d("MAINACTIVITY_TAG", "OnError: " + error.getMessage());
            }
        }, Contants.API_GATEWAY + "/registry/devices"));

        volleyService.doRequest(new GetRoomsRequest(new VolleyCallback<ArrayList<Room>>() {
            @Override
            public void OnResponse(ArrayList<Room> data) {
                Log.d("MAINACTIVITY_TAG", "OnResponse: " + data.toString());

                rooms.clear();
                rooms.addAll(data);

                for (Room room : rooms)
                    Log.d("MAINACTIVITY_TAG", "OnParsing: " + room.toString());
            }

            @Override
            public void OnError(Error error) {
                Log.d("MAINACTIVITY_TAG", "OnError: " + error.getMessage());
            }
        }, Contants.API_GATEWAY + "/registry/rooms"));
    }
}
