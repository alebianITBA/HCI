package edu.itba.hci.chopi.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import edu.itba.hci.chopi.R;
import edu.itba.hci.chopi.activity.BaseActivity;
import edu.itba.hci.chopi.activity.LogInActivity;
import edu.itba.hci.chopi.activity.OrdersActivity;

/**
 * Created by kevinkraus on 19/11/14.
 */
public class BroadcastManager extends BroadcastReceiver {



    private static final String KEY_ONLY_WIFI = "onlywifi";
    public static final String KEY_SOUND = "notificationsound";
    private static final int UPDATE_NOTIFICATION_ID = 001;
    private static final int NEW_ORDER = 101;
    private static final int UPDATED_ORDER_STATUS = 102;
    private static final int UPDATED_ORDER_POSITION = 103;
    private static HashMap<String, Order> previousOrders;
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (checkConnection(context)) {

            SharedPreferences settings = context.getSharedPreferences(
                    LogInActivity.LOGIN_DATA, 0);
            String username = settings.getString("USERNAME", null);
            String auth_Token = settings.getString("AUTH_TOKEN", null);
            if (username == null || auth_Token == null) {
                //No quiero que mande notificacion si no esta logueado.
//                sendNotification(context,
//                        context.getString(R.string.notification_error_notloggedin),
//                        context.getString(R.string.notification_error_notloggedin),0);
            } else {

                new CheckForUpdates()
                        .execute("http://eiffel.itba.edu.ar/hci/service3/Order.groovy?method=GetAllOrders&username="
                                + username
                                + "&authentication_token="
                                + auth_Token);
                System.out
                        .println("http://eiffel.itba.edu.ar/hci/service3/Order.groovy?method=GetAllOrders&username="
                                + username
                                + "&authentication_token="
                                + auth_Token);
            }
        }

    }




    private class CheckForUpdates extends AsyncTask<String, Void, String> {

        public void onPostExecute(String result) {

            String error = null;
            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(result);
                error = jsonObject.getString("error");
            } catch (JSONException e) {
                Log.w("onPostExecuteProcessOrder", e.getLocalizedMessage());
            }

            if (error == null) {
                sendNotification(comparateOrder(jsonObject));
            } else {
                sendNotification(context, context.getString(R.string.notification_error),
                        context.getString(R.string.notification_error),0,null);
                System.out.println(jsonObject);
            }
        }

        @Override
        protected String doInBackground(String... urls) {
            return BaseActivity.readJSON(urls[0]);
        }

    }


    private ArrayList<Change> comparateOrder(JSONObject current) {
        HashMap<String, Order> currentOrdersMap = new HashMap<String, Order>();
        ArrayList<Change> changes = new ArrayList<Change>();
        try {
            JSONArray corders = current.getJSONArray("orders");

            for (int i = 0; i < corders.length(); i++) {
                currentOrdersMap.put(corders.getJSONObject(i).getString("id"),new Order(corders.getJSONObject(i).getString("id"), corders.getJSONObject(i).getString("status"),corders.getJSONObject(i).getString("latitude"), corders.getJSONObject(i).getString("longitude")));
            }
        } catch (JSONException e) {
            System.out.println(current);
            e.printStackTrace();
        }

        if(previousOrders == null){
            previousOrders = currentOrdersMap;
            return changes;
        }
        Set<String> set = currentOrdersMap.keySet();
        Order currOrder;
        Order prevOrder;
        for (String curr : set) {
            currOrder = currentOrdersMap.get(curr);
            if (previousOrders.containsKey(curr)) {
                prevOrder = previousOrders.get(curr);
                if (!currOrder.status.equals(prevOrder.status)) {
                    changes.add(new Change(currOrder, UPDATED_ORDER_STATUS));
                } else if (!prevOrder.latitude.equals(currOrder.latitude)
                        || !prevOrder.longitude.equals(currOrder.longitude)) {
                    changes.add(new Change(currOrder, UPDATED_ORDER_POSITION));
                }
            } else {
                changes.add(new Change(currOrder, NEW_ORDER));
            }
        }
        previousOrders = currentOrdersMap;

        return changes;
    }




    // Check for network availability
    private boolean checkConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private void sendNotification(ArrayList<Change> changes) {

        String msg = "";
        String orderId = "";
        if (changes == null) {
            sendNotification(context, context.getString(R.string.notification_error),
                    context.getString(R.string.notification_error),0, orderId);
            return;
        }
        if (changes.isEmpty()) {
            //NO QUIERO QUE MANDE NOTIFICACIONES!
//            sendNotification(context,context.getString(R.string.notification_onupdate_nonew),context.getString(R.string.notification_onupdate_nonew),0);
            return;
        }
        for (Change change : changes) {
            int changeid = change.change;
            switch (changeid) {
                case UPDATED_ORDER_STATUS:

                    int status = Integer.valueOf(change.order.status);
                    String str = "";
                    switch(status){
                        case 1:
                            str = context.getString(R.string.notification_status_created);
                            break;
                        case 2:
                            str = context.getString(R.string.notification_status_confirmed);
                            break;
                        case 3:
                            str = context.getString(R.string.notification_status_transport);
                            break;
                        case 4:
                            str = context.getString(R.string.notification_status_delivered);
                            break;
                    }

                    msg = msg + context.getString(R.string.notification_order) + " " + change.order.id + " "
                            + str + ". ";
                    orderId=change.order.id;
                    break;
                case UPDATED_ORDER_POSITION:
                    msg = msg + context.getString(R.string.notification_order) + " " + change.order.id + " "
                            + context.getString(R.string.notification_order_updatedlocation) + ". ";
                    orderId=change.order.id;
                    break;

                case NEW_ORDER:
                    msg = msg + context.getString(R.string.notification_order) + " " + change.order.id + ": "
                            + context.getString(R.string.notification_order_new) + ". ";
                    orderId=change.order.id;

            }
        }

        sendNotification(context,context.getString(R.string.notification_onupdate),
                msg, changes.size(),orderId);

    }



    private void sendNotification(Context context, String title,
                                  String msg, int number, String orderId) {
        NotificationCompat.Builder notif = new NotificationCompat.Builder(
                context).setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentText(msg)
                .setTicker(msg);

        if(number != 0)
            notif.setNumber(number);

        Intent resultIntent = new Intent(context, OrdersActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(BaseActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notif.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        boolean value = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(KEY_SOUND, true);

        if(value){
            Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notif.setSound(uri);
        }
        mNotificationManager.notify(UPDATE_NOTIFICATION_ID, notif.build());

    }





    private class Change {
        Order order;
        int change;

        Change(Order order, int change) {
            this.order = order;
            this.change = change;
        }
    }

    private class Order {

        String id;
        String status;
        String latitude;
        String longitude;

        Order(String id, String status, String latitude, String longitude) {
            this.id = id;
            this.status = status;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }


}
