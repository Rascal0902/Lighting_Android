package com.example.myapplication;

import android.graphics.Color;
import android.os.Build;

class TurnLight{
    static int height,width;
    static float[][] Red = new float[3000][3000];
    static float[][] Green = new float[3000][3000];
    static float[][] Blue = new float[3000][3000];
    static float lightX,lightY,lightZ;
    static float lightR,lightG,lightB;
    static float power;
    static float[] Ambient ={(float)0.7,(float)0.7,(float)0.7};
    static float[] specular = {(float)0.5,(float)0.5,(float)0.5};
    static int specularpower = 32;

    static Color ambient_color(int x, int y){
        float R,G,B;
        R = (int)(Red[x][y]*Ambient[0]);
        G = (int)(Green[x][y]*Ambient[1]);
        B = (int)(Blue[x][y]*Ambient[2]);
        Color newCol = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            newCol = Color.valueOf(Color.rgb(R/255, G/255, B/255));
            return newCol;
        }
        return newCol;
    }
    static Color diffuse_color(int x,int y){
        float R,G,B;
        float[] lightVector = { lightX - x , lightY - y, lightZ };
        float mag = (float)Math.sqrt((lightX-x)*(lightX-x)+(lightY-y)*(lightY-y)+lightZ*lightZ);
        for(int i=0 ; i<3 ; i++ ){lightVector[i]/=mag;}
        float cosine = lightVector[2];
        float lambertfactor = Math.max(cosine, 0);
        float luminosity = 1/(1+mag*mag);
        R = Math.min(Red[x][y]*power*2*lightR*lambertfactor*luminosity,255);
        G = Math.min(Green[x][y]*power*2*lightG*lambertfactor*luminosity,255);
        B = Math.min(Blue[x][y]*power*2*lightB*lambertfactor*luminosity,255);
        Color newCol = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            newCol = Color.valueOf(Color.rgb(R/255, G/255, B/255));
            return newCol;
        }
        return newCol;
    }
    static Color specular_color(int x,int y){
        if(specularpower==0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Color newCol;
            newCol = Color.valueOf(Color.rgb(0,0,0));
            return newCol;
        }
        float R,G,B;
        //https://heinleinsgame.tistory.com/15
        float[] normal = new float[]{0, 0, 1};
        float[] lightVector = { lightX - x , lightY - y , lightZ }; //물체에서 광원으로
        float[] lightReflected = {x - lightX , y - lightY , lightZ};  //물체에서 빛벡터가 튕긴 방향향
        float mag = (float)Math.sqrt((x-lightX)*(x-lightX)+(y-lightY)*(y-lightY)+lightZ*lightZ);
        for(int i=0 ; i<3 ; i++){lightReflected[i]/=mag;}
        float[] viewDir = {0.0F,0.0F,1.0F};//normalized
        float spec = (float) Math.pow(Math.max(viewDir[0]*lightReflected[0]+viewDir[1]*lightReflected[1]+viewDir[2]*lightReflected[2],0.0),specularpower);
        R = specular[0]*spec*lightR*Red[x][y];
        G = specular[1]*spec*lightG*Green[x][y];
        B = specular[2]*spec*lightB*Blue[x][y];
        Color newCol = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            newCol = Color.valueOf(Color.rgb(R/255, G/255, B/255));
            return newCol;
        }
        return newCol;
    }
    static Color lambert_color(int x,int y){
        Color ambient = ambient_color(x,y);
        Color diffuse = diffuse_color(x,y);
        Color specular = specular_color(x,y);
        float finalR,finalG,finalB;
        Color newCol = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            finalR = Math.min(((ambient.red() + diffuse.red()+specular.red()) * 255), 255);
            finalG = Math.min(((ambient.green() + diffuse.green()+specular.green()) * 255), 255);
            finalB = Math.min(((ambient.blue() + diffuse.blue()+specular.blue()) * 255), 255);
            newCol = Color.valueOf(Color.rgb(finalR/255, finalG/255, finalB/255));
            return newCol;
        }
        return newCol;
    }
}