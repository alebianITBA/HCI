package edu.itba.hci.chopi.api;

import android.util.Log;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import edu.itba.hci.dto.Products;

/**
 * Created by kevinkraus on 19/11/14.
 */
public class ProductsBySubcategoryIdRequest extends RetrofitSpiceRequest<Products, ApiCalls> {

    private static final int MAX_NUMBER_OF_ITEMS = 1000;
    private static final String method = "GetProductsBySubcategoryId";

    private int id;
    private String filter;
    public ProductsBySubcategoryIdRequest(int id,String filter) {
        super(Products.class, ApiCalls.class);
        this.id = id;
        this.filter = filter;
    }

    @Override
    public Products loadDataFromNetwork() {
        Log.d(ProductsByNameRequest.class.getSimpleName(), "Call GetProductsBySubcategoryId api");
        Products products = getService().getProductsBySubcategoryId(method, id,filter, MAX_NUMBER_OF_ITEMS);
        return products;
    }
}
