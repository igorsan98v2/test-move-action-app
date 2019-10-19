package com.ygs.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.ygs.testing.controller.DBAccess;
import com.ygs.testing.controller.MotionController;
import com.ygs.testing.listeners.ButtonListener;
import com.ygs.testing.listeners.MotionEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private MotionEventListener motionEventListener=new MotionEventListener();
    private Button toStatButton;
    private TextView actionStatus;
    private final int TIMER_PERIOD =500;
    private final int ACTION_FAIL_TIME = 1000;
    private final int ACTION_TIME = 10000;
    private final int FAIL_CODE =0;
    private final int SUCCESS_CODE =1;
    private String progress ;
    private String request;
    private String success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor motionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(motionEventListener,motionSensor,SensorManager.SENSOR_DELAY_NORMAL);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionStatus = findViewById(R.id.action_status);
        toStatButton =findViewById(R.id.button);
        toStatButton.setOnClickListener(new ButtonListener());
        progress = getResources().getString(R.string.action_in_progress);
        success = getResources().getString(R.string.action_respond);
        request = getResources().getString(R.string.action_request);

    }

    @Override
    protected void onResume() {
        super.onResume();

        final MotionController controller = new MotionController(motionEventListener,this);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int iter =0;
            int prvsStatus =0;
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                       boolean change = controller.isStillMove();
                       String action_status="";
                       //fail case

                       if(iter*TIMER_PERIOD% ACTION_FAIL_TIME ==0 && !change){
                            iter=0;
                            if(prvsStatus!=0) {
                                action_status = request;
                                controller.sendStat(FAIL_CODE);
                                Log.i("STAT", "FAIL" + action_status);
                            }
                            prvsStatus=0;
                       }
                       //success case
                       else if(iter*TIMER_PERIOD%ACTION_TIME==0 && iter!=0)
                       {
                           action_status = success;
                           Log.i("STAT","success"+action_status);
                           controller.sendStat(SUCCESS_CODE);
                           iter=0;
                           prvsStatus =1;
                       }
                       //continue case
                       else{
                           action_status = progress;
                           Log.i("STAT","progress" + action_status);
                           iter++;
                           prvsStatus =2;
                       }
                       changeInfo(action_status);
                    }
                });
            }
        };
        timer.schedule(task,0,TIMER_PERIOD);
    }

    private void changeInfo(String action){
        actionStatus.setText(action);
    }

}
