package edu.itba.hci.chopi.api;

import android.util.Log;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import edu.itba.hci.dto.Categories;
import edu.itba.hci.dto.Product;
import edu.itba.hci.dto.Products;

/**
 * Created by kevinkraus on 8/11/14.
 */
public class ProductsByCategoryRequest extends RetrofitSpiceRequest<Products, ApiCalls> {

    private static final String method = "GetProductsByCategoryId";
    private int categoryId;
    private int size =1000;

    public ProductsByCategoryRequest(int categoryId) {
        super(Products.class, ApiCalls.class);
        this.categoryId = categoryId;
    }

    @Override
    public Products loadDataFromNetwork() {
        Log.d(CategoriesRequest.class.getSimpleName(), "Call GetProductsByCategoryId api");
        Products products =getService().getProductsByCategoryId(method, categoryId, size,"");
        return products;
    }
}
