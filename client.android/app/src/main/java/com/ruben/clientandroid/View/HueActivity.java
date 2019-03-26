package com.ruben.clientandroid.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ruben.clientandroid.Api.volley.VolleyService;
import com.ruben.clientandroid.Models.Device;
import com.ruben.clientandroid.Models.HueController;
import com.ruben.clientandroid.Models.Lamp;
import com.ruben.clientandroid.R;

import java.util.ArrayList;

public class HueActivity extends AppCompatActivity {
    private ArrayList<Lamp> lamps;
    private VolleyService volleyService;

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

            }
        }, Contants.API_GATEWAY + "/hue/lamps"));*/
        // Test Data
        lamps.add(new Lamp("1", "Lamp 1", false, 255, 255, 255));
    }
}
