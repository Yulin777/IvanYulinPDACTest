package com.yulin.ivan.pdactest.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

/**
 * Created by Ivan Y. on 2019-10-13.
 */

@Entity(tableName = "topColors", primaryKeys = {"color"})
public class TopColor {
    public int color;

    public int counter;

    public TopColor(Integer color, Integer counter) {
        this.color = color;
        this.counter = counter;
    }

    /**
     * @return int array size 3. arr[0] is R value, arr[1] is G value, arr[2] is B value
     */
    public int[] getRGB() {

        /* extract colors bitwise for ARBG_8888*/
        int R = (color & 0xff0000) >> 16;
        int G = (color & 0x00ff00) >> 8;
        int B = (color & 0x0000ff) >> 0;

        return new int[]{R, G, B};
    }

    @NonNull
    @Override
    public String toString() {

        int[] rgb = getRGB();
        return "R: " + rgb[0] + " G: " + rgb[1] + " B: " + rgb[2];
    }
}
