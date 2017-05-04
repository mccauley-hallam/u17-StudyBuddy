package com.mccauley.database;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class SetReminder extends ActionBarActivity
{
    Button btnBack;
    String nameOfUser, name;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        Intent intent1 = getIntent();
        Bundle bd = intent1.getExtras();
        nameOfUser = (String) bd.get("username");
        name = (String) bd.get("name");
        btnBack = (Button) findViewById(R.id.btn_Back);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SetReminder.this, Main.class);
                SetReminder.this.startActivity(intent);
                intent.putExtra("username", nameOfUser);
                intent.putExtra("name", name);
            }
        });

        final EditText eTentry = (EditText) findViewById(R.id.et_entry);
        final EditText eTdate = (EditText) findViewById(R.id.et_date);
        final EditText eTtime = (EditText) findViewById(R.id.et_time);
        final Button btnInsert = (Button) findViewById(R.id.btn_Insert);

        btnInsert.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Get text from user input
                final String username = nameOfUser;
                final String entry = eTentry.getText().toString();
                final String time = eTtime.getText().toString();
                final String date = eTdate.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            //Success, Fail, Catch Error
                            if (success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(SetReminder.this);
                                builder.setMessage("Successfully set reminder! You can now view this on the homepage!")
                                        .setNegativeButton("Continue", null)
                                        .create()
                                        .show();
                                eTentry.setText("");
                                eTtime.setText("");
                                eTdate.setText("");
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SetReminder.this);
                                builder.setMessage("Error: Unable to insert entry. Please try again...")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                //Variable registerRequest for reference in RegisterRequest.java
                SetReminder_Log setReminder_Log = new SetReminder_Log(username, entry, time, date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SetReminder.this);
                queue.add(setReminder_Log);
            }
        });
    }
}
