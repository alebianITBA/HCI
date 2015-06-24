package edu.itba.hci.chopi.api;

import android.util.Log;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import edu.itba.hci.dto.Categories;

public class CategoriesRequest extends RetrofitSpiceRequest<Categories, ApiCalls> {

    private static final String method = "GetAllCategories";

    public CategoriesRequest() {
        super(Categories.class, ApiCalls.class);
    }

    @Override
    public Categories loadDataFromNetwork() {
        Log.d(CategoriesRequest.class.getSimpleName(), "Call GetAllCategories api");
        return getService().getCategories(method);
    }
}
