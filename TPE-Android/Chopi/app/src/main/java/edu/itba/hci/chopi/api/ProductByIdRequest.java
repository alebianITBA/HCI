package edu.itba.hci.chopi.api;

import android.util.Log;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import edu.itba.hci.chopi.activity.ProductsListActivity;
import edu.itba.hci.dto.Categories;
import edu.itba.hci.dto.Product;
import edu.itba.hci.dto.ProductFromApi;

/**
 * Created by kevinkraus on 8/11/14.
 */
public class ProductByIdRequest extends RetrofitSpiceRequest<ProductFromApi, ApiCalls> {

    private static final String method = "GetProductById";
    private int productId;

    public ProductByIdRequest(int productId) {
        super(ProductFromApi.class, ApiCalls.class);
        this.productId = productId;
    }

    @Override
    public ProductFromApi loadDataFromNetwork() {
        Log.w(ProductsListActivity.class.getSimpleName(), "Call GetProductById api, id: " + productId);
        return getService().getProductById(method, productId);
    }
}
