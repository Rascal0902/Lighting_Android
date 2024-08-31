package com.example.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterActivity extends Activity {

    private Button mSubmitButton;

    private EditText mName, mID, mPassword, mTel;

    private CheckBox checkbox;

    private static String userName, userId, userPassword, userTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerxml);
        mID = (EditText) findViewById(R.id.registerID);
        mPassword = (EditText) findViewById(R.id.registerPassword);
        mName = (EditText) findViewById(R.id.registerName);
        mTel = (EditText) findViewById(R.id.registerTel);
        mSubmitButton=findViewById(R.id.submitButton);
        checkbox=findViewById(R.id.checkBox);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = mName.getText().toString();
                userId = mID.getText().toString();
                userPassword = mPassword.getText().toString();
                userTel = mTel.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        System.out.println("!");
                        try{
                            System.out.println(response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){ // 회원가입이 가능한다면
                                if(checkbox.isChecked()) {
                                    Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                                else{Toast.makeText(getApplicationContext(), "약관에 동의하셔야 사용 가능합니다.", Toast.LENGTH_SHORT).show();}
                            }else{Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다. 다시 한 번 확인해 주세요.", Toast.LENGTH_SHORT).show();return;}
                        }
                        catch(Exception e){
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
                System.out.println(userId+ userPassword+ userName+ userTel);
                RegisterRequest registerRequest = new RegisterRequest(userId, userPassword, userName, userTel, responseListener,errorListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });
    }
}
