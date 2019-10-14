package com.yulin.ivan.pdactest.activities.main.fragments.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.util.Log;

import androidx.camera.core.ImageAnalysis;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.yulin.ivan.pdactest.model.CameraViewModel;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.PriorityQueue;
import java.util.Queue;

import static com.yulin.ivan.pdactest.activities.main.MainActivity.TOP_N;

/**
 * Created by Ivan Y. on 2019-10-13.
 */

class CameraPresenter {
    //MAX_COLORS is defined to dilute the possible colors, since all top 5 always were 0.00.... % with TextureView
    //after testing, the way i used to separate between colors wasnt correct
    private static final int MAX_COLORS = (int) Math.pow(256, 3) + 1;//1000;
    private CameraViewModel cameraViewModel;

    void setViewModel(FragmentActivity activity) {
        cameraViewModel = ViewModelProviders.of(activity).get(CameraViewModel.class);
    }


    /**
     * captured image analyzer
     * the algorithm is here
     * each color's counter is incremented once found in the captured image
     */
    ImageAnalysis.Analyzer getImageAnalyzer() {
        return (imageProxy, rotationDegrees) -> {

            final Bitmap bitmap = imageToBitmap(imageProxy.getImage(), rotationDegrees);//textureView.getBitmap();


            if (bitmap == null) return;

            int[] intColors = new int[MAX_COLORS];

            for (int x = 0; x < imageProxy.getWidth(); x++) {
                for (int y = 0; y < imageProxy.getHeight(); y++) {

                    int pixel = bitmap.getPixel(x, y);
                    int abspixel = pixel * -1;
                    int closestIndex = CameraPresenter.this.getClosestIndex(abspixel);
                    intColors[closestIndex]++;
                }
            }
            CameraViewModel.imageResolution = imageProxy.getWidth() * imageProxy.getHeight();
            cameraViewModel.insert(CameraPresenter.this.getTopN(intColors));
        };
    }

    /**
     * this one is copied from stackOverflow
     * i used TextureView at first and used its bitmap, but the TextureView was not good in landscape
     * switched to CameraView which doesnt have a "getBitmap()" method
     * didnt think it was part of the algorithm so looked it up online
     */
    private Bitmap imageToBitmap(Image image, float rotationDegrees) {

        assert (image.getFormat() == ImageFormat.NV21);

        // NV21 is a plane of 8 bit Y values followed by interleaved  Cb Cr
        ByteBuffer ib = ByteBuffer.allocate(image.getHeight() * image.getWidth() * 2);

        ByteBuffer y = image.getPlanes()[0].getBuffer();
        ByteBuffer cr = image.getPlanes()[1].getBuffer();
        ByteBuffer cb = image.getPlanes()[2].getBuffer();
        ib.put(y);
        ib.put(cb);
        ib.put(cr);

        YuvImage yuvImage = new YuvImage(ib.array(),
                ImageFormat.NV21, image.getWidth(), image.getHeight(), null);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0,
                image.getWidth(), image.getHeight()), 50, out);
        byte[] imageBytes = out.toByteArray();
        Bitmap bm = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        Bitmap bitmap = bm;

        // On android the camera rotation and the screen rotation
        // are off by 90 degrees, so if you are capturing an image
        // in "portrait" orientation, you'll need to rotate the image.
        if (rotationDegrees != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotationDegrees);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bm,
                    bm.getWidth(), bm.getHeight(), true);
            bitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }

    /**
     * for colors dilution
     *
     * @param abspixel the pixel needed to be sorted to its closest color
     * @return the closes available index for current pixel
     */
    private int getClosestIndex(int abspixel) {
        double step = (Math.pow(256, 3) + 1) / MAX_COLORS;
        return (int) (abspixel / step);
    }

    /**
     * decided to use PriorityQueue to avoid sorting each time a new color is inserted
     *
     * @param counters all the colors read from the image
     *                 the index is the color itslef (in int format)
     *                 the value is the counter of this particular color
     * @return a PriorityQueue of pairs, with size n, where the key of each pair is the color (in int format) and the value is its counter
     */
    private Queue<Pair<Integer, Integer>> getTopN(int[] counters) {
        int smallestCounter = 0;
        double step = (Math.pow(256, 3) + 1) / MAX_COLORS;

        Queue<Pair<Integer, Integer>> queue = new PriorityQueue<>(TOP_N, (p1, p2) -> {
            if (p1.second != null && p2.second != null) {
                return p2.second.compareTo(p1.second);
            }
            return 0;
        });

        for (int i = 0; i < counters.length; i++) {
            if (counters[i] > smallestCounter) {
                smallestCounter = counters[i];

                queue.add(new Pair<>((int) (i * -1 * step), counters[i]));

                if (queue.size() > TOP_N) queue.poll(); //remove 6th smallest
            }
        }

        Log.d("top n: ", queue.toString());
        return queue;
    }


    interface CameraViewFragment {
        //no callback methods are actually needed for this one
    }
}
