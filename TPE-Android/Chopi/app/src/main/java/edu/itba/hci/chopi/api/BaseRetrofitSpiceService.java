package edu.itba.hci.chopi.api;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;



public class BaseRetrofitSpiceService extends RetrofitGsonSpiceService {

    private final static String BASE_URL = "http://eiffel.itba.edu.ar/hci/service3";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }
}