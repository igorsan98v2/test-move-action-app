package com.ygs.testing.services;

import com.ygs.testing.util.Energy;


import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.PUT;
/**
 * interface for accessing to restful api
 *
 * */
public interface JSONPlaceHolderApi {
    /**
     * put request to operationStatus api
     *
     * @param energy using for  send status of try energy losing  to server
     * @return {@link Call<ResponseBody>}  contain JSON body with answer that can be converted by {@link com.google.gson.Gson}
     * to  {@link Energy}
     * */
    @PUT("api/energy/operationStatus")
    Call<ResponseBody> sendData(@Body Energy energy);
}
