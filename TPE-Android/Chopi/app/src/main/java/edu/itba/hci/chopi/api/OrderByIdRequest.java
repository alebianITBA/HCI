package edu.itba.hci.chopi.api;

import android.util.Log;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import edu.itba.hci.chopi.activity.ProductsListActivity;
import edu.itba.hci.dto.Order;
import edu.itba.hci.dto.OrderFromApi;
import edu.itba.hci.dto.Product;

/**
 * Created by kevinkraus on 18/11/14.
 */
public class OrderByIdRequest extends RetrofitSpiceRequest<OrderFromApi, ApiCalls> {

    private static final String method = "GetOrderById";
    private int orderId;
    private String username;
    private String authentication_token;

    public OrderByIdRequest(String username, String authentication_token, int orderId) {
        super(OrderFromApi.class, ApiCalls.class);
        this.orderId = orderId;
        this.username = username;
        this.authentication_token = authentication_token;
    }

    @Override
    public OrderFromApi loadDataFromNetwork() {
        Log.d(ProductsListActivity.class.getSimpleName(), "Call GetOrderById api");
     return getService().getOrderById(method, username, authentication_token, orderId);

    }
}