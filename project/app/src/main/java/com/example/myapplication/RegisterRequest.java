package com.example.myapplication;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    // 서버 url 설정 (php 파일 연동)
    final static private String URL = "http://gghs20.cafe24.com/Register20046.php"; // "http:// 퍼블릭 DNS 주소/Register.php"
    private Map<String, String> parameters;
    public RegisterRequest(String userID, String userPassword, String userName, String userAge, Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Request.Method.POST, URL, listener, errorListener);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userAge", userAge);
    }
    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}