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
    public static final int TIMER_PERIOD =500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor motionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(motionEventListener,motionSensor,SensorManager.SENSOR_DELAY_NORMAL);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionStatus = findViewById(R.id.action_status);
        toStatButton =findViewById(R.id.button);
        toStatButton.setOnClickListener(new ButtonListener(this));


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
                        String status = controller.detectAction();
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

}
