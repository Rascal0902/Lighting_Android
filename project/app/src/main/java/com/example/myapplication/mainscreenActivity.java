package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;

public class mainscreenActivity  extends AppCompatActivity {
    imageFragmentActivity fragment1;
    lightviewFragmentActivity fragment2;
    setdataFragmentActivity fragment3;
    ViewPager2 pager;
    TabLayout tabs;
    MyPagerAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RootController.initialize();
        setContentView(R.layout.mainscreen);
        pager = findViewById(R.id.pager);
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setOffscreenPageLimit(3);
        adapter = new MyPagerAdapter(getSupportFragmentManager(), getLifecycle());
        pager.setAdapter(adapter);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        tabs.setScrollPosition(0, 0f, true);
                        return;
                    case 1:
                        tabs.setScrollPosition(1, 0f, true);
                        return;
                    case 2:
                        tabs.setScrollPosition(2, 0f, true);
                }
            }
        });
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {pager.setCurrentItem(0);}
                else if (position == 1) {pager.setCurrentItem(1);}
                else if (position == 2) {pager.setCurrentItem(2);}
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
    class MyPagerAdapter extends FragmentStateAdapter {
        int itemCount = 3;
        public MyPagerAdapter(FragmentManager fm, Lifecycle cycle) {
            super(fm, cycle);
        }
        @Override
        public int getItemCount() {return itemCount;}
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0: {fragment1 = new imageFragmentActivity();fragmentsetting1();return fragment1;}
                case 1: {fragment2 = new lightviewFragmentActivity();fragmentsetting2();return fragment2;}
                case 2: {fragment3 = new setdataFragmentActivity();fragmentsetting3();return fragment3;}
            }
            fragment1 = new imageFragmentActivity();fragmentsetting1();return fragment1;
        }
    }
    Bitmap before, after;
    byte[] beforebyte, afterbyte;
    public void fragmentsetting1(){
        beforebyte = getIntent().getByteArrayExtra("image");
        afterbyte = beforebyte;
        Bitmap before = BitmapFactory.decodeByteArray(beforebyte, 0, beforebyte.length);
        RootController.beforeImage = before;
        (new RootController()).loadBtn();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        float scale = (float) (1024/(float)ChangeImage.afterImage.getWidth());
        int image_w = (int) (ChangeImage.afterImage.getWidth() * scale);
        int image_h = (int) (ChangeImage.afterImage.getHeight() * scale);
        Bitmap resize = Bitmap.createScaledBitmap(ChangeImage.afterImage, image_w, image_h, true);
        resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        afterbyte = stream.toByteArray();
        Bundle bundle = new Bundle();
        bundle.putByteArray("afterimage",afterbyte);
        fragment1.setArguments(bundle);
        setdataFragmentActivity.init=true;
    }
    public void fragmentsetting2(){
        setdataFragmentActivity.init=true;
    }
    public void fragmentsetting3(){}
}


