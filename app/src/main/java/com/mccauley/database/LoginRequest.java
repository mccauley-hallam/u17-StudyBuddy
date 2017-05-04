package com.mccauley.database;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class LoginRequest extends StringRequest{

    //PHP Script location
    private static final String LOGIN_REQUEST_URL = "http://30158616.pe.hu/login.php";
    private Map<String, String> params;

    //Get details from Database $ check if correct
    public LoginRequest(String username, String password, Response.Listener<String> listener)
    {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    //Return username $ password
    @Override
    public Map<String, String> getParams()
    {
        return params;
    }
}
