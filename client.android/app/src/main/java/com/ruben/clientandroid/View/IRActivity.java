package com.ruben.clientandroid.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ruben.clientandroid.Models.Device;
import com.ruben.clientandroid.Models.IrController;
import com.ruben.clientandroid.R;

public class IRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ir);
        IrController controller = new IrController((Device) getIntent().getSerializableExtra("DEVICE"));

    }
}
