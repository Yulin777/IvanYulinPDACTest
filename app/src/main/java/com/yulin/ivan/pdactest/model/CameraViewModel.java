package com.yulin.ivan.pdactest.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Ivan Y. on 2019-10-13.
 */

public class CameraViewModel extends AndroidViewModel {
    public static double imageResolution;
    private TopColorsDao topColorsDao;
    private LiveData<List<TopColor>> topColors;

    public CameraViewModel(Application application) {
        super(application);
        TopColorsDatabase topColorsDatabase = TopColorsDatabase.getDatabase(application);
        topColorsDao = topColorsDatabase.topColorsDao();
        topColors = topColorsDao.getTopColors();
    }

    public LiveData<List<TopColor>> getTopColors() {
        return topColors;
    }

    public void loadTopColors(List<TopColor> colorsLost) {
        topColorsDao.deleteAll();
        new insertAsyncTask(topColorsDao).execute(colorsLost);
    }

    public void insert(Queue<Pair<Integer, Integer>> topN) {
        List<TopColor> topColorList = new ArrayList<>();

        for (Pair<Integer, Integer> pair : topN) {
            topColorList.add(new TopColor(pair.first, pair.second));
        }

        loadTopColors(topColorList);
    }


    private static class insertAsyncTask extends AsyncTask<List<TopColor>, Void, Void> {

        private TopColorsDao mAsyncTaskDao;

        insertAsyncTask(TopColorsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<TopColor>... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
