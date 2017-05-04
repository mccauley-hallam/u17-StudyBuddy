package com.mccauley.database;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutAct extends AppCompatActivity
{

    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        btnBack = (Button) findViewById(R.id.btnGoBack);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AboutAct.this, Main.class);
                AboutAct.this.startActivity(intent);
            }
        });
    }
}
