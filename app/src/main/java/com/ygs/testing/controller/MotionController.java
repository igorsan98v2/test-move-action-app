package com.ygs.testing.controller;

import android.content.Context;

import android.util.Log;

import com.ygs.testing.MainActivity;
import com.ygs.testing.R;
import com.ygs.testing.listeners.MotionEventListener;
import com.ygs.testing.services.NetworkService;
import com.ygs.testing.util.Energy;

import static com.ygs.testing.MainActivity.TIMER_PERIOD;


/**
 * @author Ihor Yutsyk
 * class use for contoll user motion by using {@link MotionEventListener}
 * to get values of accel from sensor of {@link android.hardware.Sensor} type Gravity;
 * deciding status of energy loss progress ;
 * sending stat to restful service with report of energy loss try;
 * saving data energy loses to db
 *
 * */
public class MotionController {
    private MotionEventListener eventListener;
    private Context context;
    private float[] prvsValues = null;

    //magic values
    private static final float ACCURANCY =0.1f;//using for decide is move was done
    private static final int ACTION_FAIL_TIME = 1000;//time that must gone to fail energy loss
    private static final int ACTION_TIME = 10000;//time that must gone till phone is move to lose energy successfully

    private static final int FAIL_CODE =0;
    private  static final int SUCCESS_CODE =1;
    private static final int CONTINUE_CODE =2;


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
    /**decide if move was done in last TIMER_PERIOD (see {@link MainActivity})
     * @return true if move was done
     * @return false if move was`nt done
     * */
    public boolean isStillMove(){

        float[]curValues =  eventListener.getValues();//getting values from sensor

       if(prvsValues!=null&&curValues!=null){
            int unMoveCounter = 0;//count of same values in every axis
            for(int i=0;i<curValues.length;i++){
                if(curValues[i]-prvsValues[i]<=ACCURANCY)
                    unMoveCounter++;
            }
           prvsValues = curValues.clone();
           if(unMoveCounter==curValues.length)return false;

       }

       if(curValues!=null&&prvsValues==null){
            prvsValues = curValues.clone();
            return false;//if we here it`s firs call of isStillMove
       }
       return true;
    }
    /**send statistic to API service {@link NetworkService}
     * save status of action in local storage by {@link DBAccess}
     * @throws NumberFormatException to prevent sending wrong info to service and writing to db
     */
    public void  sendStat(int status) throws NumberFormatException{
        Energy energy = new Energy();
        energy.setStatus(status);
        DBAccess.getInstace(context).writeStat(energy);
        NetworkService.getInstance().sendEnergy(energy);

    }
    /**decide type of action that make user
     * action status can have three variants:
     * <ul>
     *     <li>{@link #request}</li>
     *     <li>{@link #success}</li>
     *     <li>{@link #progress}</li>
     * <ul/>
     * also send info to api  and save data to db
     * @return action status
     * */
    public String detectAction(){
        boolean change = isStillMove();
        String actionStatus=request;
        //fail case
        if(failCondition(change)){
            actionStatus =  failAction();
        }
        //success case
        else if(successCondition())
        {
           actionStatus =successAction();
        }
        //continue case
        else{
            actionStatus = continueAction();
        }
        return  actionStatus;
    }


    private boolean failCondition(boolean change){
        return  iter* TIMER_PERIOD% ACTION_FAIL_TIME ==0 && !change && iter>=0;
    }
    private boolean successCondition(){
        return (iter* TIMER_PERIOD % ACTION_TIME==0 && iter>0) || iter<0;
    }
    private String successAction(){

        Log.i("STAT","success"+success);
        if(iter>0&&prvsStatus==CONTINUE_CODE)
            try {
                sendStat(SUCCESS_CODE);
            }
            catch (NumberFormatException e){
                e.printStackTrace();

            }
        else if(iter!=-6){
                iter++;

        }
        else iter =- 6;//make delay
        prvsStatus = SUCCESS_CODE;
        return  success;
    }
    private String failAction(){
        iter=0;
        Log.i("STAT", "FAIL" + request);
        if(prvsStatus!=FAIL_CODE) {
            try {
                sendStat(FAIL_CODE);
            }
            catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        prvsStatus=FAIL_CODE;
        return  request;
    }
    private String continueAction(){
        iter++;
        prvsStatus =CONTINUE_CODE;
        Log.i("STAT","progress " + progress);
        return progress;
    }
}
