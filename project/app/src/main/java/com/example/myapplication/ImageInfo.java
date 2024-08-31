package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;

public class ImageInfo{
    public static int width;
    public static int height;
    private static boolean hasAlphaChannel;
    private static int pixelLength;
    private static int[] pixels = new int[9000000];

    @SuppressLint("SuspiciousIndentation")
    ImageInfo(Bitmap image) {
        width = (int) image.getWidth();
        height = (int) image.getHeight();
        hasAlphaChannel = ChangeImage.beforeImage.hasAlpha();
        pixelLength = 3;
        if (hasAlphaChannel)
            pixelLength = 4;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    color = image.getColor(x, y);
                    int pos = (y * pixelLength * width) + (x * pixelLength);
                    if (hasAlphaChannel)
                    pixels[pos++] = (int) (color.alpha()*255); // Alpha
                    pixels[pos++] = (int) (color.blue()*255); // Blue
                    pixels[pos++] = (int) (color.green()*255); // Green
                    pixels[pos++] = (int) (color.red()*255);// Red
                }
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    static int[] getRGB(int x, int y) {
        int pos = (y * pixelLength * width) + (x * pixelLength);
        int rgb[] = new int[4];
        if (hasAlphaChannel)
            rgb[3] = pixels[pos++]; // Alpha
            rgb[2] = pixels[pos++]; // Blue
            rgb[1] = pixels[pos++]; // Green
            rgb[0] = pixels[pos++]; // Red
        return rgb;
    }
}
