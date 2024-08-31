package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import java.io.IOException;

public class setdataFragmentActivity extends Fragment{
    EditText lightx,lighty,lightz,info;
    SeekBar power,lightr,lightg,lightb,ambient,specular;
    static ViewGroup rootview;
    static Boolean init = true;
    Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = (ViewGroup)inflater.inflate(R.layout.setdatafragment,container,false);
        return rootview;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        if(init) {setinfo();init = false;}
        lightx.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {LightXThread threadx = new LightXThread();threadx.start();}
        });
        lighty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {LightYThread thready = new LightYThread();thready.start();}
        });
        lightz.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {LightZThread threadz = new LightZThread();threadz.start();}
        });
        power.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {LightPThread threadp = new LightPThread();threadp.start();}
        });
        lightr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LightRThread threadr = new LightRThread();threadr.start();
                System.out.println(lightr.getProgress() * 255 / 100);}
        });
        lightg.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LightGThread threadg = new LightGThread();threadg.start();
                System.out.println(lightg.getProgress() * 255 / 100);}
        });
        lightb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LightBThread threadb = new LightBThread();threadb.start();
                System.out.println(lightb.getProgress() * 255 / 100);}
        });
        ambient.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {LightAThread threadamb = new LightAThread();threadamb.start();}
        });
        specular.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {LightSThread threads = new LightSThread();threads.start();}
        });
    }
    public void nothingselected(){
        System.out.println("nothing selected!!");
        lightx.setText("0");lighty.setText("0");lightz.setText("0");
        lightr.setProgress(0);lightg.setProgress(0);lightb.setProgress(0);
        power.setProgress(0);ambient.setProgress(0);specular.setProgress(0);
    }
    @SuppressLint("SetTextI18n")
    public void setinfo() {
        lightx = (EditText) rootview.findViewById(R.id.lightx);
        lighty = (EditText) rootview.findViewById(R.id.lighty);
        lightz = (EditText) rootview.findViewById(R.id.lightz);
        info = (EditText) rootview.findViewById(R.id.information);

        power = (SeekBar) rootview.findViewById(R.id.power);
        lightr = (SeekBar) rootview.findViewById(R.id.lightr);
        lightg = (SeekBar) rootview.findViewById(R.id.lightg);
        lightb = (SeekBar) rootview.findViewById(R.id.lightb);
        ambient = (SeekBar) rootview.findViewById(R.id.ambient);
        specular = (SeekBar) rootview.findViewById(R.id.specular);

        info.setText("Width : " + ChangeImage.beforeImage.getWidth()+ " Height : "+ChangeImage.beforeImage.getHeight());
        info.setEnabled(false);

        if (RootController.selected != 0) {
            System.out.println(ChangeImage.lightX[RootController.selected]);
            lightx.setText(Integer.toString(ChangeImage.lightX[RootController.selected]));
            lighty.setText(Integer.toString(ChangeImage.lightY[RootController.selected]));
            lightz.setText(Integer.toString(ChangeImage.lightZ[RootController.selected]));
            lightr.setProgress(ChangeImage.lightR[RootController.selected] * 100 / 255);
            lightg.setProgress(ChangeImage.lightG[RootController.selected] * 100 / 255);
            lightb.setProgress(ChangeImage.lightB[RootController.selected] * 100 / 255);
            power.setProgress((int) ChangeImage.power[RootController.selected]/100);
            ambient.setProgress((int) (ChangeImage.ambient[RootController.selected]*100));
            specular.setProgress((int)(ChangeImage.specular[RootController.selected]));
        } else{
            lightx.setText("0");lighty.setText("0");lightz.setText("0");
            lightr.setProgress(0);lightg.setProgress(0);lightb.setProgress(0);power.setProgress(0);
            ambient.setProgress(0);specular.setProgress(0);
        }
    }
    class LightXThread extends Thread{
        public void run(){
            handler.post(new Runnable(){
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    int temp =0;
                    try {
                        temp = Integer.parseInt(String.valueOf(lightx.getText()));
                    }catch(Exception e){
                        lightx.setText("0");
                    }
                    if(temp > ChangeImage.beforeImage.getWidth()){temp = ChangeImage.beforeImage.getWidth();
                    lightx.setText(Integer.toString(temp));}
                    if(temp < 0){temp = 0;lightx.setText("0");}
                    ChangeImage.lightX[RootController.selected] = temp;
                }
            });
        }
    }
    class LightYThread extends Thread{
        public void run(){
            handler.post(new Runnable(){
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    int temp =0;
                    try {
                        temp = Integer.parseInt(String.valueOf(lighty.getText()));
                    }catch(Exception e){
                        lighty.setText("0");
                    }
                    if(temp > ChangeImage.beforeImage.getHeight()){temp = ChangeImage.beforeImage.getHeight();
                    lighty.setText(Integer.toString(temp));}
                    if(temp < 0){temp = 0;lighty.setText("0");}
                    ChangeImage.lightY[RootController.selected] = temp;
                }
            });
        }
    }
    class LightZThread extends Thread{
        public void run(){
            handler.post(new Runnable(){
                @Override
                public void run() {
                    int temp =0;
                    try {
                        temp = Integer.parseInt(String.valueOf(lightz.getText()));
                    }catch(Exception e){
                        lightz.setText("0");
                    }
                    if(temp<0){temp = 0;lightz.setText("");}
                    ChangeImage.lightZ[RootController.selected] = temp;
                }
            });
        }
    }
    class LightRThread extends Thread{
        public void run(){
            handler.post(new Runnable(){
                @Override
                public void run() {
                    ChangeImage.lightR[RootController.selected] = lightr.getProgress() * 255 / 100;
                }
            });
        }
    }
    class LightGThread extends Thread{
        public void run(){
            handler.post(new Runnable(){
                @Override
                public void run() {
                    ChangeImage.lightG[RootController.selected] = lightg.getProgress() * 255 / 100;
                }
            });
        }
    }
    class LightBThread extends Thread{
        public void run(){
            handler.post(new Runnable(){
                @Override
                public void run() {
                    ChangeImage.lightB[RootController.selected] = lightb.getProgress() * 255 / 100;
                }
            });
        }
    }
    class LightPThread extends Thread{
        public void run(){
            handler.post(new Runnable(){
                @Override
                public void run() {
                    ChangeImage.power[RootController.selected] = power.getProgress()*100;
                }
            });
        }
    }
    class LightAThread extends Thread{
        public void run(){
            handler.post(new Runnable(){
                @Override
                public void run() {
                    ChangeImage.ambient[RootController.selected] = (float)(ambient.getProgress()/100.0);
                }
            });
        }
    }
    class LightSThread extends Thread{
        public void run(){
            handler.post(new Runnable(){
                @Override
                public void run() {
                    ChangeImage.specular[RootController.selected] = specular.getProgress();
                }
            });
        }
    }
}
