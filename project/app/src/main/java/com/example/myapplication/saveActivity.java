package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class saveActivity  extends AppCompatActivity {
    TextView notcon, con;
    Button finishbutton;
    ProgressBar progress;
    static Bitmap bitmap;
    static String savename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savingxml);

        notcon = findViewById(R.id.convert);
        con = findViewById(R.id.converted);

        progress = findViewById(R.id.saveprogress);
        finishbutton = findViewById(R.id.finishing);
        if(ChangeImage.afterImage != null){
            converted();
        }
        finishbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RootController.saveBtn();
                Intent intent = new Intent(getApplicationContext(),finishActivity.class);;
                startActivityForResult(intent,0);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Context context = saveActivity.this;
        System.out.println(context);
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, savename , "");
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE));
    }
    void converted(){
            notcon.setVisibility(View.INVISIBLE);
            con.setVisibility(View.VISIBLE);
            progress.setVisibility(View.INVISIBLE);
    }
}

