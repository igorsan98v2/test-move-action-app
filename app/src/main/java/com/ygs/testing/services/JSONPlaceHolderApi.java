package com.ygs.testing.services;

import com.ygs.testing.util.Energy;


import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface JSONPlaceHolderApi {
    @PUT("api/energy/operationStatus")
    Call<ResponseBody> sendData(@Body Energy energy);
}
