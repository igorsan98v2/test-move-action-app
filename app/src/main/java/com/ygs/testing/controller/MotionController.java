package com.ygs.testing.controller;

import android.content.Context;

import android.util.Log;

import com.ygs.testing.MainActivity;
import com.ygs.testing.R;
import com.ygs.testing.listeners.MotionEventListener;
import com.ygs.testing.services.NetworkService;
import com.ygs.testing.util.Energy;



/**
 * @author Ihor Yutsyk
 * class use for conrolling user motion
 * deciding status of energy loss progress;
 * sending stat to restful service with report of energy loss try
 * saving data energy loses to db
 *
 * */
public class MotionController {
    private MotionEventListener eventListener;
    private Context context;
    private float[] prvsValues = null;

    private float ACCURANCY =0.1f;//
    private final int ACTION_FAIL_TIME = 1000;
    private final int ACTION_TIME = 10000;
    private final int FAIL_CODE =0;
    private final int SUCCESS_CODE =1;

    private int prvsStatus=0;
    private int iter=0;

    private String success;
    private String progress;
    private String request;


    public MotionController(MotionEventListener eventListener, Context context){
        this.eventListener = eventListener;
        this.context = context;
        success = context.getResources().getString(R.string.action_respond);
        progress =  context.getResources().getString(R.string.action_in_progress);
        request =  context.getResources().getString(R.string.action_request);
        DBAccess.getInstace(context);
    }
    public boolean isStillMove(){
        float[]curValues =  eventListener.getValues();


       if(prvsValues!=null&&curValues!=null){
            int unMoveCounter = 0;
            for(int i=0;i<curValues.length;i++){
                if(curValues[i]-prvsValues[i]<=ACCURANCY)
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
    public void  sendStat(int status){
        Energy energy = new Energy();
        energy.setStatus(status);
        DBAccess.getInstace(context).writeStat(energy);
        NetworkService.getInstance().sendEnergy(energy);

    }

    public String detectAction(){
        boolean change = isStillMove();
        String actionStatus=request;
        //fail case

        if(iter* MainActivity.TIMER_PERIOD% ACTION_FAIL_TIME ==0 && !change){
            iter=0;
            actionStatus = request;
            Log.i("STAT", "FAIL" + actionStatus);
            if(prvsStatus!=0) {
                sendStat(FAIL_CODE);

            }
            prvsStatus=0;
        }
        //success case
        else if(iter*MainActivity.TIMER_PERIOD%ACTION_TIME==0 && iter!=0)
        {
            actionStatus = success;
            Log.i("STAT","success"+actionStatus);
            sendStat(SUCCESS_CODE);
            iter=0;
            prvsStatus =1;
        }
        //continue case
        else{
            actionStatus = progress;
            Log.i("STAT","progress" + actionStatus);
            iter++;
            prvsStatus =2;
        }
        return  actionStatus;
    }
}
