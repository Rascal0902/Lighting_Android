package com.example.myapplication;

import android.os.Bundle;
import android.provider.DocumentsContract;
import android.sax.RootElement;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

public class lightviewFragmentActivity extends Fragment{
    Button lightnew, lightdelete, lightapply;
    ListView lightlist;
    ViewGroup rootview;
    static String[] List = {};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = (ViewGroup)inflater.inflate(R.layout.lightviewfragment,container,false);
        return rootview;
    }
    @Override
    public void onStart() {
        super.onStart();
        lightnew = rootview.findViewById(R.id.lightNew);
        lightdelete = rootview.findViewById(R.id.lightDelete);
        lightapply = rootview.findViewById(R.id.lightApply);
        lightlist = rootview.findViewById(R.id.lightList);
        ArrayAdapter Adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, List) ;
        lightlist.setAdapter(Adapter);
        lightlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String strText = (String) parent.getItemAtPosition(position) ;
                RootController.selected = position+1;
                setdataFragmentActivity.init = true;
            }
        }) ;
        lightnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(List.length>RootController.maxselected){System.out.println("max lights!!");return;}
                RootController.now ++;
                ChangeImage.now = RootController.now;
                String[] ListTemp = new String[List.length+1];
                for(int i=0; i<List.length ; i++){
                    ListTemp[i] = List[i];
                }
                String temp = "light" + RootController.now;
                ListTemp[List.length] = temp;
                List = ListTemp;
                ArrayAdapter Adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, List) ;
                lightlist.setAdapter(Adapter);
                return;
            }
        });

        lightdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(List.length<=RootController.minselected){System.out.println("min lights!!");return;}
                ChangeImage.lightX[RootController.selected]=0;ChangeImage.lightY[RootController.selected]=0;ChangeImage.lightZ[RootController.selected]=0;
                ChangeImage.lightR[RootController.selected]=255;ChangeImage.lightG[RootController.selected]=255;ChangeImage.lightB[RootController.selected]=255;
                ChangeImage.power[RootController.selected] = 0;ChangeImage.ambient[RootController.selected] = 1;ChangeImage.specular[RootController.selected] = 32;
                if(RootController.selected == RootController.now){RootController.selected--;}
                setdataFragmentActivity.init = true;
                String[] ListTemp = new String[List.length-1];
                for(int i=0; i<List.length-1 ; i++){
                    ListTemp[i] = List[i];
                }
                List = ListTemp;
                RootController.now--;
                ChangeImage.now = RootController.now;
                ArrayAdapter Adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, List) ;
                lightlist.setAdapter(Adapter);
                return;
            }
        });

        lightapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RootController.settingBtn();
            }
        });
    }
}
