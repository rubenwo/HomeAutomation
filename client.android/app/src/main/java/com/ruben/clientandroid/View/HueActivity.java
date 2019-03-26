package com.ruben.clientandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.ruben.clientandroid.Adapters.HueRecyclerAdapter;
import com.ruben.clientandroid.R;
import com.ruben.clientandroid.Api.volley.VolleyService;
import com.ruben.clientandroid.Models.Device;
import com.ruben.clientandroid.Models.HueBridge;
import com.ruben.clientandroid.Models.HueController;
import com.ruben.clientandroid.Models.HueLamp;

import java.util.ArrayList;

public class HueActivity extends AppCompatActivity {
    private ArrayList<HueLamp> lamps;

    public HueBridge hueBridge;
    public VolleyService volleyService;
    public ArrayList<HueLamp> hueLamps;
    private RecyclerView mRecyclerview;
    private RecyclerView.LayoutManager mLayoutManager;
    private HueRecyclerAdapter hueRecyclerAdapter;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hue);
        lamps = new ArrayList<>();
        volleyService = VolleyService.getInstance(null);
        HueController controller = new HueController((Device) getIntent().getSerializableExtra("DEVICE"));
        /*
        volleyService.doRequest(new GetLampsRequest(new VolleyCallback<ArrayList<Lamp>>() {
            @Override
            public void OnResponse(ArrayList<Lamp> data) {
                lamps.clear();
                lamps.addAll(data);
            }

            @Override
            public void OnError(Error error) {
                //TODO: Do something in case of an error.
            }
        }, Contants.API_GATEWAY + "/hue/lamps"));*/
        // Test Data
        lamps.add(new HueLamp(1, "Lamp 1", false, 255, 255, 255, "hs", true));
    }

    public void createCardView() {
        mRecyclerview = (RecyclerView) findViewById(R.id.activity_hue_recycler_view);
        mRecyclerview.setHasFixedSize(true);

    }
}
