package com.ygs.testing.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ygs.testing.listeners.MotionEventListener;
import com.ygs.testing.services.NetworkService;
import com.ygs.testing.util.Energy;

import java.util.TimerTask;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MotionController {
    private float[] prvsValues = null;
    private float accLevel =0.1f;//
    private MotionEventListener eventListener;
    private Context context;
    public MotionController(MotionEventListener eventListener, Context context){
        this.eventListener = eventListener;
        this.context = context;
        DBAccess.getInstace(context);
    }
    public boolean isStillMove(){
        float[]curValues =  eventListener.getValues();


       if(prvsValues!=null&&curValues!=null){
            int unMoveCounter = 0;
            for(int i=0;i<curValues.length;i++){
                if(curValues[i]-prvsValues[i]<=accLevel)
                    unMoveCounter++;
            }
           prvsValues = curValues.clone();
           if(unMoveCounter==curValues.length)return false;

       }
       if(curValues!=null){
        prvsValues = curValues.clone();
       }
       if(prvsValues==null||curValues==null)return false;
       return true;
    }
    public  void sendStat(int status){
        Energy energy = new Energy();
        energy.setStatus(status);
        NetworkService.getInstance()
                .getJSONApi().sendData(energy)
                .enqueue(new Callback<Energy>() {
                    @Override
                    public void onResponse(@NonNull Call<Energy> call, @NonNull Response<Energy> response) {
                        if(response.body()!=null) {
                            DBAccess.getInstace(context).writeStat(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Energy> call, @NonNull Throwable t) {

                        t.printStackTrace();
                    }
                });
    }


}