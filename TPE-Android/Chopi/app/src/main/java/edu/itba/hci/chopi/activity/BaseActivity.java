package edu.itba.hci.chopi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.itba.hci.chopi.DrawerAdapter;
import edu.itba.hci.chopi.DrawerItem;
import edu.itba.hci.chopi.OrdersAdapter;
import edu.itba.hci.chopi.R;
import edu.itba.hci.chopi.alarm.BroadcastManager;
import edu.itba.hci.chopi.api.BaseService;
import edu.itba.hci.chopi.api.BaseSpiceManager;
import edu.itba.hci.chopi.api.CategoriesRequest;
import edu.itba.hci.chopi.api.OrdersRequest;
import edu.itba.hci.chopi.api.ProductByIdRequest;
import edu.itba.hci.dto.Categories;
import edu.itba.hci.dto.Orders;

public class BaseActivity extends Activity {

    private BaseSpiceManager baseSpiceManager = new BaseSpiceManager(BaseService.class);
    private DrawerLayout cDrawerLayout;
    private ActionBarDrawerToggle cDrawerToggle;
    private ListView cDrawerList;
    private SharedPreferences SP;
    private boolean notLogged;
    private Intent intent;
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter filter;

    @Override
    protected void onStart() {
        baseSpiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcastReceiver = new BroadcastManager();
        filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(broadcastReceiver, filter);


    }

    @Override
    protected void onStop() {
        baseSpiceManager.shouldStop();
        super.onStop();
    }

    public SpiceManager getBaseSpiceManager() {
        return baseSpiceManager;
    }

    public void setDrawer() {
        cDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        cDrawerList = (ListView) findViewById(R.id.left_drawer);
        cDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        cDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                cDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
            }
        };
        setDrawerItems();
        cDrawerLayout.setDrawerListener(cDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        cDrawerToggle.syncState();
    }

    private void setDrawerItems() {
        String[] titulos;
        ArrayList<DrawerItem> drawerItems = new ArrayList<DrawerItem>();
        TypedArray drawerIcons;

        drawerIcons = getResources().obtainTypedArray(R.array.drawer_icons);
        titulos = getResources().getStringArray(R.array.drawer_options);

        SP = getSharedPreferences(LogInActivity.LOGIN_DATA, 0);
        notLogged = SP.getString("AUTH_TOKEN", "FAIL") == "FAIL";

        drawerItems.add(new DrawerItem(titulos[0], drawerIcons.getResourceId(0, -1)));
        drawerItems.add(new DrawerItem(titulos[1], drawerIcons.getResourceId(1, -1)));
        if (notLogged) {
            drawerItems.add(new DrawerItem(titulos[2], drawerIcons.getResourceId(2, -1)));
        } else {
            drawerItems.add(new DrawerItem(titulos[3], drawerIcons.getResourceId(4, -1)));
            drawerItems.add(new DrawerItem(titulos[4], drawerIcons.getResourceId(4, -1)));
        }
        cDrawerList.setAdapter(new DrawerAdapter(this, drawerItems));
        cDrawerList.setBackgroundColor(Color.parseColor("#fff3f3f3"));

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        cDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (cDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_search:
                //openSearch();
                return true;
            //		case R.id.ditem_text:
            //			Intent intent2 = new Intent(this, LogInActivity.class);
            //			startActivity(intent2);
            //			return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selectItem(int position) {

        Log.d("MyApp", String.valueOf(position));
        Log.e("MyApp", String.valueOf(position));
        Log.i("MyApp", String.valueOf(position));
        Log.v("MyApp", String.valueOf(position));
        switch (position) {
            case 0:
                intent = new Intent(this, MainActivity.class);
                break;
            case 1:
                intent = new Intent(this, CategoriesActivity.class);
                break;
            case 2:
                if (notLogged) {
                    intent = new Intent(this, LogInActivity.class);
                } else {
                    intent = new Intent(this, OrdersActivity.class);

                }
                break;
            case 3:
                if (notLogged) {
                    intent = new Intent(this, MainActivity.class);
                } else {

                    Toast.makeText(this, R.string.loggingout, Toast.LENGTH_LONG);


                    onLogOut();
                    SystemClock.sleep(1500);
                    intent = new Intent(this, MainActivity.class);
                }
                break;
            default:
                intent = new Intent(this, MainActivity.class);
                break;
        }
        startActivity(intent);
    }

    public boolean checkConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void showConnectionError() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.connectionProblem);
        alertDialog.setMessage(getString(R.string.connectionLost));
        alertDialog.setButton(-1, getString(R.string.close), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setIcon(R.drawable.ic_launcher);
        alertDialog.show();
    }

    public static String readJSON(String URL) {
        System.out.println(URL);
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.w("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.w("readJSONFeed", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }

    public void onLogOut() {
        if (checkConnection()) {
            SharedPreferences settings = getSharedPreferences(LogInActivity.LOGIN_DATA, 0);
            String auth_token = settings.getString("AUTH_TOKEN", "FAIL");
            String username = settings.getString("USERNAME", "FAIL");
            new processLogOut()
                    .execute("http://eiffel.itba.edu.ar/hci/service3/Account.groovy?method=SignOut&username="
                            + username + "&authentication_token=" + auth_token);
        } else {
            showConnectionError();
        }
    }

    private class processLogOut extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return readJSON(urls[0]);
        }

        protected void onPostExecute(String result) {
            String error = null;
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(result);
                error = jsonObject.getString("error");
            } catch (JSONException e) {
                Log.w("onPostExecuteLogOut", e.getLocalizedMessage());
            }
            if (error == null) {
                SharedPreferences settings = getSharedPreferences(LogInActivity.LOGIN_DATA, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("AUTH_TOKEN");
                editor.remove("USERNAME");
                editor.commit();
            }


        }
    }


}
