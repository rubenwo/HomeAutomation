package com.ruben.clientandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.ruben.clientandroid.Api.volley.VolleyService;
import com.ruben.clientandroid.DetailedHueLampActivity;
import com.ruben.clientandroid.Models.HueBridge;
import com.ruben.clientandroid.Models.HueLamp;
import com.ruben.clientandroid.R;

import static com.ruben.clientandroid.MainActivity.BRIDGE_URL;
import static com.ruben.clientandroid.MainActivity.LAMP_URL;

import java.io.Serializable;
import java.util.ArrayList;

public class HueRecyclerAdapter extends RecyclerView.Adapter<HueRecyclerAdapter.LightViewHolder> implements Serializable {

    private Context context;
    private ArrayList<HueLamp> hueLamps;
    private HueBridge bridge;
    public VolleyService api;

    public HueRecyclerAdapter(Context context, ArrayList<HueLamp> dataArgs, HueBridge bridge) {
        hueLamps = dataArgs;
        this.context = context;
        this.bridge = bridge;
    }

    @NonNull
    @Override
    public LightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lamp_item, parent, false);
        return new LightViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull final LightViewHolder viewHolder, int position) {
        HueLamp lamp = hueLamps.get(position);
        viewHolder.name.setText(lamp.getName());
        viewHolder.state.setChecked(lamp.isOn());
        viewHolder.seekBar.setMin(0);
        viewHolder.seekBar.setMax(254);
        viewHolder.seekBar.setThumbOffset(0);
        viewHolder.seekBar.setProgress(lamp.brightness);

        api = VolleyService.getInstance(null); //Enter Application here
    }

    @Override
    public int getItemCount() {
        return hueLamps.size();
    }

    public void clear() {
        hueLamps.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<HueLamp> lamps) {
        hueLamps.addAll(lamps);
        notifyDataSetChanged();
    }

    public class LightViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private Switch state;
        private SeekBar seekBar;

        public LightViewHolder(View view, final Context ctx) {
            super(view);
            context = ctx;
            name = view.findViewById(R.id.lamp_item_name);
            state = view.findViewById(R.id.lamp_item_switch);
            seekBar = view.findViewById(R.id.lamp_item_seekbar);

            view.setOnClickListener((View v) -> {
                HueLamp lamp = hueLamps.get(getAdapterPosition());
                Intent intent = new Intent(context, DetailedHueLampActivity.class); //Enter DetailedActivity.class here
                intent.putExtra(BRIDGE_URL, (Parcelable) bridge);
                intent.putExtra(LAMP_URL, (Parcelable) lamp);

                context.startActivity(intent);
            });

        }
    }
}
