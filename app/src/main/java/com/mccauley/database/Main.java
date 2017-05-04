package com.mccauley.database;

/*
    Todo: Fix Java to show PHP Array

    Todo: Experiment with caching strings
    Todo: https://developer.android.com/training/basics/data-storage/files.html
*/

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    Button btnLogout;
    String name, username, message, email, gotName, gotUsername, gotEmail;
    TextView welcome;

    private Button btnRefresh;
    private TextView textViewResult;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnRefresh = (Button) findViewById(R.id.btn_Refresh);
        textViewResult = (TextView) findViewById(R.id.textViewResult);

        btnRefresh.setOnClickListener(this);

        /*Intent loginIntent = getIntent();
        welcome = (TextView) findViewById(R.id.wlcMess);
        name = loginIntent.getStringExtra("name");
        username = loginIntent.getStringExtra("username");
        email = loginIntent.getStringExtra("email");

        message = "Hello, " + gotName + "!";
        welcome.setText(message);

        Bundle gotBundle = getIntent().getExtras();
        gotName = gotBundle.getString("name");
        gotUsername = gotBundle.getString("username");
        gotEmail = gotBundle.getString("email");*/



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "By, McCauley Hallam | South Devon College | Project", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);

        TextView navbarName = (TextView)hView.findViewById(R.id.tvUsername);
        TextView navbarUsername = (TextView)hView.findViewById(R.id.tvName);
        navbarName.setText(name);
        navbarUsername.setText(username);

        navigationView.setNavigationItemSelectedListener(this);

        btnLogout = (Button) findViewById(R.id.bLogout);
        btnLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intentLogout = new Intent(Main.this, LoginActivity.class);
                Main.this.startActivity(intentLogout);
            }
        });
    }

    private void getData()
    {
        String userid = username;
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = Config.DATA_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(Main.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response)
    {
        String id = "";
        String usernm = "";
        String entry = "";
        String time = "";
        String date = "";

        ArrayList<String> retrieved = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);

            int x = 0;

            while(x < result.length())
            {
                JSONObject fetch = result.getJSONObject(x);
                id = fetch.getString(Config.KEY_ID);
                usernm = fetch.getString(Config.KEY_USER);
                entry = fetch.getString(Config.KEY_ENTRY);
                time = fetch.getString(Config.KEY_TIME);
                date = fetch.getString(Config.KEY_DATE);
                retrieved.add("Entry: " + entry + "\nTime: "+ time + "\nDate: " + date);
                //retrieved.add("ID: " + id + "\nName: "+ usernm +"\nEntry: " +entry+ "\nTime: "+ time + "\nDate: " + date);

                ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, retrieved);

                ListView listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(adapter);
                x++;
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        //textViewResult.setText("ID: " + id + "\nName: "+ usernm +"\nEntry: " +entry+ "\nTime: "+ time + "\nDate: " + date);
    }

    public void onClick(View v)
    {
        getData();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            // Handle the camera action
        }
        else if (id == R.id.nav_setReminder)
        {
            Intent intent1 = new Intent(Main.this, SetReminder.class);
            intent1.putExtra("username", username);
            intent1.putExtra("name", name);
            Main.this.startActivity(intent1);
        }
        else if (id == R.id.nav_games)
        {

        }
        else if (id == R.id.nav_about)
        {
            Intent intent2 = new Intent(Main.this, AboutAct.class);
            Main.this.startActivity(intent2);
        }
        else if (id == R.id.nav_settings)
        {
            Intent intent3 = new Intent(Main.this, SettingsAct.class);
            Main.this.startActivity(intent3);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
