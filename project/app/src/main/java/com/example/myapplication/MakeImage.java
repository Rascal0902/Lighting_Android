package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

public class MakeImage{
    private static int width,height;
    private static Bitmap newimage;
    private static Color[][] resultscolor = new Color[3000][3000];
    MakeImage(int width,int height,boolean hasalphachannel){
        this.width=width; this.height=height;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            newimage = Bitmap.createBitmap(this.width,this.height, Bitmap.Config.ARGB_8888,hasalphachannel);
        }
    }
    static void setPixel(int x,int y,Color col){
        int color = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            color = ((int)col.alpha() & 0xff) << 24 | ((int)(col.red()*255) & 0xff) << 16 | ((int)(col.green()*255) & 0xff) << 8 | ((int)(col.blue()*255) & 0xff);
            newimage.setPixel(x,y,color);
        }
    }
    static Bitmap returnimage(){
        return newimage;
    }
    static void update(){
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                MakeImage.setPixel(i,j,resultscolor[i][j]);
            }
        }
    }
}