package com.mccauley.database;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class SetReminder_Log extends StringRequest
{

    //PHP Script Location
    private static final String SET_REMINDER_URL = "http://30158616.pe.hu/entry.php";
    private Map<String, String> params;

    public SetReminder_Log(String username, String entry, String time, String date, Response.Listener<String> listener)
    {
        super(Method.POST, SET_REMINDER_URL, listener, null);
        //Gets input, stores in database
        params = new HashMap<>();
        params.put("username", username);
        params.put("entry", entry);
        params.put("time", time);
        params.put("date", date);
    }

    @Override
    public Map<String, String> getParams()
    {
        return params;
    }
}
