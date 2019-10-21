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
/**
 * @author Ihor Yutsyk
 * works with UI
 * use {@link MotionController} to decide status type of energy losing
 * */
public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private MotionEventListener motionEventListener=new MotionEventListener();
    private Button toStatButton;
    private TextView actionStatus;
    Timer timer;
    //magic value
    public static final int TIMER_PERIOD =500;

    MotionController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor motionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(motionEventListener,motionSensor,SensorManager.SENSOR_DELAY_NORMAL);


        setContentView(R.layout.activity_main);
        actionStatus = findViewById(R.id.action_status);
        toStatButton =findViewById(R.id.button);
        toStatButton.setOnClickListener(new ButtonListener(this));
        controller = new MotionController(motionEventListener,this);
        Timer timer = new Timer();

    }

    @Override
    protected void onResume() {
        super.onResume();



        //provide ability to check motionEventListener every TIMER_PERIOD ms
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String status = controller.detectAction();//detecting is user ended any movement or continue doing them, or event did`nt start yet
                       changeInfo(status);
                    }
                });
            }
        };
        timer.schedule(task,0,TIMER_PERIOD);

    }

    private void changeInfo(String action){
        actionStatus.setText(action);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        DBAccess.close();
    }
    @Override
    protected  void onPause(){
        super.onPause();
        timer.cancel();
        
    }
    @Override
    protected void onStop(){
        super.onStop();
        timer.cancel();
    }
}
