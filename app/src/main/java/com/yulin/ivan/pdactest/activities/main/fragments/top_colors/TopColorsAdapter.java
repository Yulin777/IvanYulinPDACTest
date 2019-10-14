package com.yulin.ivan.pdactest.activities.main.fragments.top_colors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yulin.ivan.pdactest.R;
import com.yulin.ivan.pdactest.model.TopColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan Y. on 2019-10-13.
 */

class TopColorsAdapter extends RecyclerView.Adapter<TopColorHolder> {
    private List<TopColor> colorsList;

    public TopColorsAdapter(ArrayList<TopColor> topColors) {
        this.colorsList = topColors;
    }

    @NonNull
    @Override
    public TopColorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_color_item, parent, false);
        return new TopColorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopColorHolder holder, int position) {
        holder.configureView(colorsList.get(position));
    }

    @Override
    public int getItemCount() {
        return colorsList.size();
    }

    public void updateColorsList(List<TopColor> topColors) {
        this.colorsList.clear();
        this.colorsList.addAll(topColors);
        notifyDataSetChanged();
    }
}
