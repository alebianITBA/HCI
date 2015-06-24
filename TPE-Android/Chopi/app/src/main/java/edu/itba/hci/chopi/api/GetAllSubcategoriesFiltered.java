package edu.itba.hci.chopi.api;

import android.util.Log;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import edu.itba.hci.chopi.activity.ProductsListActivity;
import edu.itba.hci.dto.Subcategories;

/**
 * Created by kevinkraus on 20/11/14.
 */
public class GetAllSubcategoriesFiltered  extends RetrofitSpiceRequest<Subcategories, ApiCalls> {

    private static final String method = "GetAllSubcategories";
    private int id;
    private String filter;

    public GetAllSubcategoriesFiltered( int id, String filter) {
        super(Subcategories.class, ApiCalls.class);
        this.id = id;
        this.filter=filter;
    }

    @Override
    public Subcategories loadDataFromNetwork() {
        Log.d(GetAllSubcategoriesFiltered.class.getSimpleName(), "Call GetSubcategoriesFiltered api");
        return getService().getAllSubcategoriesFiltered(method, id,filter);

    }
}