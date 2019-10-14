package com.yulin.ivan.pdactest.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Created by Ivan Y. on 2019-10-13.
 */

@Database(entities = {TopColor.class}, version = 1, exportSchema = false)
public abstract class TopColorsDatabase extends RoomDatabase {

    public abstract TopColorsDao topColorsDao();

    private static volatile TopColorsDatabase INSTANCE;

    public static TopColorsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TopColorsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TopColorsDatabase.class, "topColors")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
