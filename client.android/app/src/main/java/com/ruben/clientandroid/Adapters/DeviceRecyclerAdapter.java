package com.ruben.clientandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruben.clientandroid.Models.Device;
import com.ruben.clientandroid.R;
import com.ruben.clientandroid.View.HueActivity;
import com.ruben.clientandroid.View.IRActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.MyViewHolder> implements Serializable {

    private Context mContext;
    private ArrayList<Device> dataSource;

    public DeviceRecyclerAdapter(Context context, ArrayList<Device> dataArgs) {
        dataSource = dataArgs;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        return new MyViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Device device = dataSource.get(i);
        myViewHolder.title.setText(device.getName());
        myViewHolder.type.setText(device.getDevice_type());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView type;
        private View background;

        public MyViewHolder(View view, final Context context) {
            super(view);
            title = view.findViewById(R.id.device_name);
            type = view.findViewById(R.id.device_type);
            background = view.findViewById(R.id.device_view);

            switch (dataSource.get(getAdapterPosition()).getDevice_type().toLowerCase()) {
                case "hue":
                    background.setOnClickListener(v -> {
                        Device i = dataSource.get(getAdapterPosition());
                        Intent intent = new Intent(context, HueActivity.class);
                        intent.putExtra("DEVICE", i);
                        mContext.startActivity(intent);
                    });
                    break;
                case "ir-controller":
                    background.setOnClickListener(v -> {
                        Device i = dataSource.get(getAdapterPosition());
                        Intent intent = new Intent(context, IRActivity.class);
                        intent.putExtra("DEVICE", i);
                        mContext.startActivity(intent);
                    });
                    break;
                default:
                    break;
            }
        }
    }
}