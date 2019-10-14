package com.yulin.ivan.pdactest.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import static com.yulin.ivan.pdactest.activities.main.MainActivity.TOP_N;

/**
 * Created by Ivan Y. on 2019-10-13.
 */

@Dao
public interface TopColorsDao {

    @Query("SELECT * FROM topColors ORDER BY counter DESC LIMIT " + TOP_N)
    LiveData<List<TopColor>> getTopColors();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<TopColor> colorsList);


    @Query("DELETE FROM topColors")
    void deleteAll();
}
