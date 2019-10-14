package com.yulin.ivan.pdactest.activities.main.fragments.top_colors;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.yulin.ivan.pdactest.model.CameraViewModel;
import com.yulin.ivan.pdactest.model.TopColor;

import java.util.List;

/**
 * Created by Ivan Y. on 2019-10-13.
 */

public class TopColorsPresenter {

    private static CameraViewModel cameraViewModel;
    private static TopColorsViewFragment topColorsViewFragment;

    public static void assignView(TopColorsViewFragment topColorsFragment) {
        topColorsViewFragment = topColorsFragment;
        cameraViewModel = ViewModelProviders.of((Fragment) topColorsFragment).get(CameraViewModel.class);
        listenToUpdates();
    }

    private static void listenToUpdates() {
        cameraViewModel.getTopColors().observeForever(topColors -> {
            topColorsViewFragment.updateTopColors(topColors);
        });
    }


    interface TopColorsViewFragment {

        /**
         * @param topColors list of paris where the key is the color (in int format) and the value is it's counter
         */
        void updateTopColors(List<TopColor> topColors);
    }
}
