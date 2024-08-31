package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {
    EditText mID, mPassword;
    Button mIdSignInButton, mIdSignUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginxml);
        mID = (EditText) findViewById(R.id.loginID);
        mPassword = (EditText) findViewById(R.id.loginPassword);
        mIdSignInButton = (Button) findViewById(R.id.loginbutton);
        mIdSignUpButton = (Button) findViewById(R.id.registerbutton);
        mIdSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = mID.getText().toString();
                String userPassword = mPassword.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                String userID = jsonResponse.getString("userID");
                                String userPassword = jsonResponse.getString("userPassword");
                                Intent intent = new Intent(getApplicationContext(), picturesettingActivity.class);// 로그인 하면서 사용자 정보 넘기기
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPassword", userPassword);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                Response.ErrorListener errorListener=new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("에러 -> "+error.getMessage());
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener,errorListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });
        mIdSignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

