package com.yulin.ivan.pdactest.activities.main.fragments.camera;

import android.Manifest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.view.CameraView;
import androidx.fragment.app.Fragment;

import com.yulin.ivan.pdactest.R;
import com.yulin.ivan.pdactest.activities.main.fragments.camera.CameraPresenter.CameraViewFragment;

import java.util.Objects;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;
import static androidx.core.content.PermissionChecker.checkSelfPermission;

/**
 * created by I.Y on 12/10/2019
 */
public class CameraFragment extends Fragment implements CameraViewFragment {

    private static final int CAMERA_REQUEST_PERMISSION_CODE = 11;
    private CameraView cameraView;
    private boolean isCameraBound = false;
    private CameraPresenter mPresenter;


    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    public CameraFragment() {
        this.mPresenter = new CameraPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cameraView = view.findViewById(R.id.view_camera);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.setViewModel(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();

        if (checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.CAMERA) != PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_PERMISSION_CODE);
        } else {
            startCamera();
        }
    }

    private void startCamera() {
        if (isCameraBound) return;

        ImageAnalysisConfig analysisConfig = new ImageAnalysisConfig.Builder()
                .setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
                .setImageQueueDepth(1) //note: analyze one image at a a time
                .build();

        ImageAnalysis imageAnalysis = new ImageAnalysis(analysisConfig);
        imageAnalysis.setAnalyzer(AsyncTask::execute, mPresenter.getImageAnalyzer());

        CameraX.bindToLifecycle(this, imageAnalysis);
        cameraView.bindToLifecycle(this);
        isCameraBound = true; // note: that's a workaround to avoid crash after onStop. CameraX.isBound(imageAnalysis) always returns false
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_PERMISSION_CODE:
                if (permissions.length < 1) return;

                if (permissions[0].equals(Manifest.permission.CAMERA) && grantResults[0] == PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    Toast.makeText(getActivity(), "please allow camera permission", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
