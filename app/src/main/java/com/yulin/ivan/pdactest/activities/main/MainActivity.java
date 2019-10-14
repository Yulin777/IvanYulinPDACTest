package com.yulin.ivan.pdactest.activities.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.yulin.ivan.pdactest.R;
import com.yulin.ivan.pdactest.activities.main.fragments.camera.CameraFragment;
import com.yulin.ivan.pdactest.activities.main.fragments.top_colors.TopColorsFragment;

/**
 * Created by Ivan Y. on 2019-10-13.
 */

public class MainActivity extends AppCompatActivity {

    public static final int TOP_N = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            setCameraFragment();
            setPopularityFragment();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        hideBars();
    }

    private void setPopularityFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.popularity_frame, TopColorsFragment.newInstance())
                .commitNow();
    }

    private void setCameraFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.camera_frame, CameraFragment.newInstance())
                .commitNow();
    }


    private void hideBars() {
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        });
    }

}
