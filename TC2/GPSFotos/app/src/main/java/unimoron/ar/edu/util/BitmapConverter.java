package unimoron.ar.edu.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;

import java.io.File;

/**
 * Created by mariano on 14/01/18.
 */

public class BitmapConverter {


    public static Bitmap convertBitmap(String pathDir, String name){
        File root = Environment.getExternalStorageDirectory();
        Bitmap bMap = BitmapFactory.decodeFile(root+"/" + pathDir +  "/" + name );
        return bMap;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap b = Bitmap.createScaledBitmap(image, width, height, true);
        return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
    }

}



