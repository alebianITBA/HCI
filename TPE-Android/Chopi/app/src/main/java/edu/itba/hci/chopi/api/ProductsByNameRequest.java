package edu.itba.hci.chopi.api;

import android.util.Log;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import edu.itba.hci.dto.Products;

/**
 * Created by kevinkraus on 17/11/14.
 */
public class ProductsByNameRequest extends RetrofitSpiceRequest<Products, ApiCalls> {

    private static final int MAX_NUMBER_OF_ITEMS = 1000;
    private static final String method = "GetProductsByName";

    private String name;

    public ProductsByNameRequest(String name) {
        super(Products.class, ApiCalls.class);
        this.name = name;
    }

    @Override
    public Products loadDataFromNetwork() {
        Log.d(ProductsByNameRequest.class.getSimpleName(), "Call GetProductsByName api");
        Products products = getService().getProductsByName(method, name, MAX_NUMBER_OF_ITEMS);
        return products;
    }
}