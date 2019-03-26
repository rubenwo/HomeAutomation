package com.ruben.clientandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.ruben.clientandroid.Contants;
import com.ruben.clientandroid.Adapters.HueRecyclerAdapter;
import com.ruben.clientandroid.Api.volley.VolleyCallback;
import com.ruben.clientandroid.Api.volley.requests.GetLampsRequest;
import com.ruben.clientandroid.R;
import com.ruben.clientandroid.Api.volley.VolleyService;
import com.ruben.clientandroid.Models.Device;
import com.ruben.clientandroid.Models.HueBridge;
import com.ruben.clientandroid.Models.HueController;
import com.ruben.clientandroid.Models.HueLamp;

import java.util.ArrayList;

public class HueActivity extends AppCompatActivity {
    public HueBridge hueBridge;
    public VolleyService volleyService;
    public ArrayList<HueLamp> hueLamps;
    private TextView title;
    private RecyclerView mRecyclerview;
    private RecyclerView.LayoutManager mLayoutManager;
    private HueRecyclerAdapter hueRecyclerAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hue);
        title = findViewById(R.id.activity_hue_title);
        title.setText("Devices:");
        hueLamps = new ArrayList<>();

        HueController controller = new HueController((Device) getIntent().getSerializableExtra("DEVICE"));
        // Test Data
        hueLamps.add(new HueLamp(1, "Lamp 1", false, 255, 255, 255, "hs", true));
        createCardView();
    }

    public void createCardView() {
        mRecyclerview = (RecyclerView) findViewById(R.id.activity_hue_recycler_view);
        mRecyclerview.setHasFixedSize(true);

        //specify a adapter:
        hueRecyclerAdapter = new HueRecyclerAdapter(this, hueLamps, hueBridge);
        mRecyclerview.setAdapter(hueRecyclerAdapter);


        //linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mLayoutManager);

        //volley for API
        volleyService = VolleyService.getInstance(null);



        volleyService.doRequest(new GetLampsRequest(new VolleyCallback<ArrayList<HueLamp>>() {
            @Override
            public void OnResponse(ArrayList<HueLamp> data) {
                hueLamps.clear();
                hueLamps.addAll(data);
                hueRecyclerAdapter.notifyDataSetChanged();

                for(HueLamp hl : hueLamps) {
                    Log.d("HueActivityTag", "OnResponse: " + hl.toString());
                }
            }

            @Override
            public void OnError(Error error) {
                Log.d("HueActivityTag", "OnError: " + error.getMessage());
            }
        }, Contants.API_GATEWAY + "/hue/lamps"));

    }

    @Override
    public void onResume() {
        super.onResume();
        //api call here!
    }
}
