package com.yulin.ivan.pdactest.activities.main.fragments.top_colors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yulin.ivan.pdactest.R;
import com.yulin.ivan.pdactest.model.TopColor;

import java.util.ArrayList;
import java.util.List;

import static com.yulin.ivan.pdactest.activities.main.MainActivity.TOP_N;

/**
 * Created by Ivan Y. on 2019-10-13.
 */

public class TopColorsFragment extends Fragment implements TopColorsPresenter.TopColorsViewFragment {

    private RecyclerView recyclerView;

    public static TopColorsFragment newInstance() {
        return new TopColorsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TopColorsPresenter.assignView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_colors, container, false);
        setupTopColorsList(view);

        return view;
    }


    private void setupTopColorsList(View container) {
        recyclerView = container.findViewById(R.id.colors_recycler_view);

        recyclerView.setHasFixedSize(true);
        TopColorsAdapter topColorsAdapter = new TopColorsAdapter(new ArrayList<>(TOP_N));
        recyclerView.setAdapter(topColorsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void updateTopColors(List<TopColor> topColors) {
        if (recyclerView != null && topColors != null && topColors.size() > 0) {

            TopColorsAdapter adapter = (TopColorsAdapter) recyclerView.getAdapter();

            if (adapter != null) {
                adapter.updateColorsList(topColors);
            }
        }
    }
}
