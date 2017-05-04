package com.mccauley.database;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest{

    //PHP Script Location
    private static final String REGISTER_REQUEST_URL = "http://30158616.pe.hu/register.php";
    private Map<String, String> params;

    //Calls RegisterRequest from RegisterActivity.java
    public RegisterRequest(String name, String username, String password, String email, Response.Listener<String> listener)
    {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        //Gets input, stores in database
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);
        params.put("email", email);
    }

    @Override
    public Map<String, String> getParams()
    {
        return params;
    }
}
