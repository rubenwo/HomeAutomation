package com.ruben.clientandroid.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ruben.clientandroid.Models.Device;
import com.ruben.clientandroid.R;

public class SettingsActivity extends AppCompatActivity {

    private TextView identifier;
    private TextView name;
    private TextView device_type;
    private TextView controller_name;
    private TextView ip_address;
    private TextView room_identifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        identifier = findViewById(R.id.settings_identifier);
        name = findViewById(R.id.settings_name);
        device_type = findViewById(R.id.settings_type);
        controller_name = findViewById(R.id.settings_controller_name);
        ip_address = findViewById(R.id.settings_ip_address);
        room_identifier = findViewById(R.id.settings_room_identifier);

        Device device = (Device) getIntent().getSerializableExtra("DEVICE");

        identifier.setText(device.getIdentifier());
        name.setText(device.getName());
        device_type.setText(device.getDevice_type());
        controller_name.setText(device.getController_name());
        ip_address.setText(device.getIp_address());
        room_identifier.setText(device.getRoom_identifier());
    }
}
