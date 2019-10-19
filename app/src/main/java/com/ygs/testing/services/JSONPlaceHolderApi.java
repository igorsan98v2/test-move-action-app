package com.ygs.testing.services;

import com.ygs.testing.util.Energy;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface JSONPlaceHolderApi {
    @PUT("api/operationStatus")
    public Call<Energy> sendData(@Body Energy energy);
}
