package com.ygs.testing;

import com.ygs.testing.listeners.MotionEventListener;

import java.util.TimerTask;


public class MotionController {
    private float[] prvsValues = null;
    private float accLevel =0.1f;//
    private MotionEventListener eventListener;
    public MotionController(MotionEventListener eventListener){
        this.eventListener = eventListener;
    }
    boolean isStillMove(){
        float[]curValues =  eventListener.getValues();


       if(prvsValues!=null&&curValues!=null){
            int unMoveCounter = 0;
            for(int i=0;i<curValues.length;i++){
                if(curValues[i]-prvsValues[i]<=accLevel)
                    unMoveCounter++;
            }
           if(unMoveCounter==curValues.length)return false;
           prvsValues = curValues.clone();
       }

       return true;
    }


}
