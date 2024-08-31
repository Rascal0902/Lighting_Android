package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;

public class imageFragmentActivity extends Fragment{
    ImageView imageview;
    ViewGroup rootView;
    Button savebutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup)inflater.inflate(R.layout.imagefragment,container,false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        imageview = rootView.findViewById(R.id.ImageAfter);
        Bundle extra = this.getArguments();
        Bitmap bitmap = null;
        if (extra != null) {
            byte[] byteArray = extra.getByteArray("afterimage");
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            System.out.println(bitmap);
            imageview.setImageBitmap(bitmap);
        }

        ChangeImage.update();
        imageview.setImageBitmap(ChangeImage.afterImage);

        savebutton = (Button) rootView.findViewById(R.id.saveButton);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saveintent = new Intent(getActivity(), saveActivity.class);
                startActivity(saveintent);
            }
        });
    }
}
