package edu.itba.hci.chopi.api;

import android.util.Log;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import edu.itba.hci.dto.Products;

/**
 * Created by kevinkraus on 17/11/14.
 */
public class ProductsByGenderRequest extends RetrofitSpiceRequest<Products, ApiCalls> {

    private static final String method = "GetAllProducts";
    private String filter;
    private int size= 1000;

    public ProductsByGenderRequest(String filter) {
        super(Products.class, ApiCalls.class);
        this.filter = filter;
    }

    @Override
    public Products loadDataFromNetwork() {
        Log.d(CategoriesRequest.class.getSimpleName(), "Call GetAllProducts api");
        Products products = getService().getProductsByGender(method, filter, size);
        return products;
    }
}