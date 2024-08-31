package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class picturesettingActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_MENU = 0;
    Button getpicture;
    RequestBuilder<Drawable> image;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picturesetting);
        getpicture = (Button) findViewById(R.id.getPicture);
        getpicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,0);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode== REQUEST_CODE_MENU){
            if(resultCode == RESULT_OK){
                InputStream in = null;
                try {
                    in = getContentResolver().openInputStream(data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    float scale = (float) (1024/(float)bitmap.getWidth());
                    int image_w = (int) (bitmap.getWidth() * scale);
                    int image_h = (int) (bitmap.getHeight() * scale);
                    Bitmap resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true);
                    resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    Intent intent = new Intent(picturesettingActivity.this, mainscreenActivity.class);
                    intent.putExtra("image", byteArray);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(resultCode == RESULT_CANCELED) {Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();}
        }
        else{Toast.makeText(this, "사진 선택 실패", Toast.LENGTH_LONG).show();}
    }
}