package com.ygs.testing.services;


import android.util.Log;

import com.google.gson.Gson;
import com.ygs.testing.util.Energy;

import java.io.IOException;
import java.io.ObjectInputStream;


import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "https://betaapi.nasladdin.club/";
    private Retrofit mRetrofit;
    private NetworkService(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }
    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }
    protected synchronized void notifyInstance(){
        notifyAll();
    }
    public JSONPlaceHolderApi getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }
    /**
     *
     * @param energy
     *
     * @throws IllegalMonitorStateException
     * @throws InterruptedException
     * */
    public  Energy sendEnergy(Energy energy){
            final Energy respondEnergy = new Energy();


                mInstance
                    .getJSONApi().sendData(energy)
                    .enqueue(new Callback<ResponseBody>() {

                        @Override
                        public   void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                            try {
                                Energy energy = new Gson().fromJson(response.body().string(),Energy.class);
                                respondEnergy.setStatus(energy.getStatus());
                                notifyInstance();
                            }
                            catch (IOException e){
                                e.printStackTrace();
                            }
                            catch (NullPointerException e){
                                e.printStackTrace();
                            }




                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

                            t.printStackTrace();
                        }
                    });




        return respondEnergy;



    }
}
