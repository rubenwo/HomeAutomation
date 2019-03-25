package com.ruben.clientandroid.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ruben.clientandroid.Models.Device;
import com.ruben.clientandroid.Models.HueController;
import com.ruben.clientandroid.R;

public class HueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hue);

        HueController controller = new HueController((Device) getIntent().getSerializableExtra("DEVICE"));

    }
}
