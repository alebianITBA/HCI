package edu.itba.hci.chopi.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import edu.itba.hci.chopi.OrdersAdapter;
import edu.itba.hci.chopi.ProductsListAdapter;
import edu.itba.hci.chopi.R;
import edu.itba.hci.chopi.api.OrderByIdRequest;
import edu.itba.hci.chopi.api.OrdersRequest;
import edu.itba.hci.dto.Order;
import edu.itba.hci.dto.OrderFromApi;
import edu.itba.hci.dto.Orders;
import edu.itba.hci.dto.Product;

/**
 * Created by alebezdjian on 13/11/14.
 */
public class OrdersActivity extends BaseActivity {
    private static ListView listOrdersView;
    private static  OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        setDrawer();

        SharedPreferences settings = getSharedPreferences(LogInActivity.LOGIN_DATA, 0);
        final String username = settings.getString("USERNAME", "FAIL");
        final String auth_Token= settings.getString("AUTH_TOKEN", "FAIL");

        getBaseSpiceManager().execute(new OrdersRequest(username,auth_Token), new OrdersRequestListener());

        listOrdersView = (ListView) findViewById(R.id.orders_list);
        listOrdersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Order order = (Order) ordersAdapter.getItem(position);
                getOrder(Integer.parseInt(order.getId()), username, auth_Token);
            }
        });
    }
    private void getOrder(int id, String username, String auth_Token) {
        OrderByIdRequest req = new OrderByIdRequest(username,auth_Token,id);
        getBaseSpiceManager().execute(req, new OrderByIdRequestListener());
    }



    private final class OrdersRequestListener implements RequestListener<Orders> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(OrdersActivity.this, "Fallo el request.", Toast.LENGTH_SHORT);
        }
        @Override
        public void onRequestSuccess(Orders orders) {
            onOrdersSuccess(orders);
        }
    }

    public void onOrdersSuccess(Orders orders) {
        ordersAdapter = new OrdersAdapter(getApplicationContext(), orders);
        listOrdersView.setAdapter(ordersAdapter);
    }

    private final class OrderByIdRequestListener implements RequestListener<OrderFromApi> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(OrdersActivity.this, "Fallo el request.", Toast.LENGTH_SHORT);
        }
        @Override
        public void onRequestSuccess(OrderFromApi order) {
            onOrderByIdSuccess(order);
        }
    }
    public void onOrderByIdSuccess(OrderFromApi order) {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra(OrderActivity.ORDER, order.getOrder());
        startActivity(intent);

    }

}
