package com.example.myapplication;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    //http://gghs20.cafe24.com/gghsdbeditor
    //id : gghs20 pw: rudrhkr20!
    final static private String URL = "http://gghs20.cafe24.com/Login20046.php";
    private Map<String, String> parameters;
    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, URL, listener, errorListener);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
    }
    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}