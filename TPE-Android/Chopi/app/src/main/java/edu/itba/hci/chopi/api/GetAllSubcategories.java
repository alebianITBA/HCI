package edu.itba.hci.chopi.api;

import android.util.Log;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import edu.itba.hci.chopi.activity.ProductsListActivity;
import edu.itba.hci.dto.OrderFromApi;
import edu.itba.hci.dto.Subcategories;

/**
 * Created by kevinkraus on 19/11/14.
 */
public class GetAllSubcategories extends RetrofitSpiceRequest<Subcategories, ApiCalls> {

    private static final String method = "GetAllSubcategories";
    private int id;

    public GetAllSubcategories( int id) {
        super(Subcategories.class, ApiCalls.class);
        this.id = id;

    }

    @Override
    public Subcategories loadDataFromNetwork() {
        Log.d(ProductsListActivity.class.getSimpleName(), "Call GetSubcategories api");
        return getService().getAllSubcategories(method, id);

    }
}