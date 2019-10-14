package com.yulin.ivan.pdactest.activities.main.fragments.top_colors;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yulin.ivan.pdactest.R;
import com.yulin.ivan.pdactest.model.TopColor;

import java.text.DecimalFormat;

import static com.yulin.ivan.pdactest.model.CameraViewModel.imageResolution;

/**
 * Created by Ivan Y. on 2019-10-13.
 */

class TopColorHolder extends RecyclerView.ViewHolder {
    private ImageView colorBackground;
    private TextView colorPercentage;
    private TextView colorDescription;


    public TopColorHolder(@NonNull View itemView) {
        super(itemView);
        this.colorBackground = itemView.findViewById(R.id.color_item_background);
        this.colorPercentage = itemView.findViewById(R.id.color_item_percentage);
        this.colorDescription = itemView.findViewById(R.id.color_item_description);
    }

    public void configureView(TopColor topColor) {
        int[] rgb = topColor.getRGB();
        colorBackground.setBackgroundColor(Color.rgb(rgb[0], rgb[1], rgb[2]));
        colorDescription.setText(topColor.toString());

        if (imageResolution != 0) {
            DecimalFormat df2 = new DecimalFormat("#.##");
            double percentage = (topColor.counter * 100) / imageResolution;

            colorPercentage.setText(df2.format(percentage) + " %");
        }
    }
}
