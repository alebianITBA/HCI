package edu.itba.hci.chopi.api;

import android.util.Log;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import edu.itba.hci.dto.Products;

/**
 * Created by kevinkraus on 20/11/14.
 */
public class ProductsByCategoryIdFiltered extends RetrofitSpiceRequest<Products, ApiCalls> {

    private static final String method = "GetProductsByCategoryId";
    private int categoryId;
    private int size =1000;
    private String filters;

    public ProductsByCategoryIdFiltered(int categoryId,String filters) {
        super(Products.class, ApiCalls.class);
        this.categoryId = categoryId;
        this.filters=filters;
    }

    @Override
    public Products loadDataFromNetwork() {
        Log.d(CategoriesRequest.class.getSimpleName(), "Call GetProductsByCategoryIdFiltered api");
        Products products =getService().getProductsByCategoryId(method, categoryId, size,filters);
        return products;
    }
}
