package edu.itba.hci.chopi.api;

import retrofit.RestAdapter;

public class BaseService extends BaseRetrofitSpiceService {

	@Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(ApiCalls.class);
    }
}
