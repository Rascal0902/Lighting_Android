package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.widget.Toast;

import java.io.IOException;

public class ChangeImage{
    public static int lightX[],lightY[],lightZ[],lightR[],lightG[],lightB[],specular[];
    public static float power[],ambient[];
    public static Bitmap beforeImage,afterImage ;
    public static int now=0;
    ChangeImage(){}
    static void setImage(Bitmap image){
        beforeImage = image;
        afterImage = image;
        TurnLight.width = (int) beforeImage.getWidth();
        TurnLight.height = (int) beforeImage.getHeight();
    }
    static void saveImage(String base){
        saveActivity.bitmap = afterImage;
        saveActivity.savename = base;
    }
    static void update() {
        ImageInfo Info = new ImageInfo(ChangeImage.beforeImage);
        MakeImage t = new MakeImage((int) ChangeImage.beforeImage.getWidth(), (int) ChangeImage.beforeImage.getHeight(), ChangeImage.beforeImage.hasAlpha());
        for (int i = 0; i < ChangeImage.beforeImage.getWidth(); i++) {
            for (int j = 0; j < ChangeImage.beforeImage.getHeight(); j++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    MakeImage.setPixel(i, j, Color.valueOf(Color.rgb(ImageInfo.getRGB(i, j)[0], ImageInfo.getRGB(i, j)[1], ImageInfo.getRGB(i, j)[2])));
                }
            }
        }
        Bitmap temp = MakeImage.returnimage();
        for (int cnt = 1; cnt <= ChangeImage.now; cnt++) {
            Info = new ImageInfo(temp);
            for (int i = 0; i < ChangeImage.beforeImage.getWidth(); i++) {
                for (int j = 0; j < ChangeImage.beforeImage.getHeight(); j++) {
                    TurnLight.Red[i][j] = (float) ImageInfo.getRGB(i, j)[0];
                    TurnLight.Green[i][j] = (float) ImageInfo.getRGB(i, j)[1];
                    TurnLight.Blue[i][j] = (float) ImageInfo.getRGB(i, j)[2];
                }
            }
            TurnLight.lightX = ChangeImage.lightX[cnt];
            TurnLight.lightY = ChangeImage.lightY[cnt];
            TurnLight.lightZ = ChangeImage.lightZ[cnt];
            TurnLight.lightR = ChangeImage.lightR[cnt];
            TurnLight.lightG = ChangeImage.lightG[cnt];
            TurnLight.lightB = ChangeImage.lightB[cnt];
            float[] temparray = new float[3];
            for (int k = 0; k < 3; k++) {
                temparray[k] = ChangeImage.ambient[cnt];
            }
            TurnLight.power = ChangeImage.power[cnt];
            TurnLight.Ambient = temparray;
            TurnLight.specularpower = ChangeImage.specular[cnt];
            for (int i = 0; i < ChangeImage.beforeImage.getWidth(); i++) {
                for (int j = 0; j < ChangeImage.beforeImage.getHeight(); j++) {
                    TurnLight.lambert_color(i, j);
                }
            }
            MakeImage tmp = new MakeImage((int) ChangeImage.beforeImage.getWidth(), (int) ChangeImage.beforeImage.getHeight(), ChangeImage.beforeImage.hasAlpha());
            for (int i = 0; i < ChangeImage.beforeImage.getWidth(); i++) {
                for (int j = 0; j < ChangeImage.beforeImage.getHeight(); j++) {
                    Color col = TurnLight.lambert_color(i, j);
                    MakeImage.setPixel(i, j, col);
                }
            }
            temp = MakeImage.returnimage();
        }
        ChangeImage.afterImage = temp;
        System.out.println("setted");
    }

}


