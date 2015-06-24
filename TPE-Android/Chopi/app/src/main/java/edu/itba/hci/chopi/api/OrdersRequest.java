package edu.itba.hci.chopi.api;

import android.util.Log;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import edu.itba.hci.dto.Orders;
import edu.itba.hci.dto.Products;

/**
 * Created by kevinkraus on 17/11/14.
 */
public class OrdersRequest extends RetrofitSpiceRequest<Orders, ApiCalls> {

    private static final String method = "GetAllOrders";
    private String username;
    private String authentication_token;
    private int size =1000;

    public OrdersRequest(String username, String authentication_token) {
        super(Orders.class, ApiCalls.class);
        this.username = username;
        this.authentication_token = authentication_token;
    }

    @Override
    public Orders loadDataFromNetwork() {
        Log.d(CategoriesRequest.class.getSimpleName(), "Call GetAllOrders api");
       return getService().getAllOrders(method, username, authentication_token);
    }
}
