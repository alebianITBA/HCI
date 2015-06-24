package edu.itba.hci.chopi.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.itba.hci.chopi.R;

public class LogInActivity extends BaseActivity {

    public final static String EXTRA_MESSAGE = "edu.itba.hci.chopi";
    public final static String LOGIN_DATA = "LOGIN_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setDrawer();
        setTitle(R.string.login);
    }



    public void onLogin(View view) {
        if (checkConnection()) {
            EditText name = (EditText) findViewById(R.id.txtUsername);
            EditText password = (EditText) findViewById(R.id.txtPassword);

            new processLogin()
                    .execute("http://eiffel.itba.edu.ar/hci/service3/Account.groovy?method=SignIn&username="
                            + name.getText().toString()
                            + "&password="
                            + password.getText().toString());

        } else {
            showConnectionError();
        }
    }

    private class processLogin extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            return readJSON(urls[0]);
        }

        protected void onPostExecute(String result) {
            System.out.println(result);
            String error = null, token = null, username = null;
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);
                error = jsonObject.getString("error");
            } catch (JSONException e) {
                Log.w("onPostExecuteLogin1", e.getLocalizedMessage());
            }

            if (error == null) {
                try {
                    token = jsonObject.getString("authenticationToken");
                    username = new JSONObject(jsonObject.getString("account")).getString("username");
                } catch (JSONException e) {
                    Log.w("onPostExecuteLogin2", e.getLocalizedMessage());
                }

                SharedPreferences settings = getSharedPreferences(LOGIN_DATA, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("AUTH_TOKEN", token);
                editor.putString("USERNAME", username);
                editor.commit();
                Toast.makeText(LogInActivity.this, getString(R.string.welcome) + " " + username, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LogInActivity.this, R.string.loginProblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
