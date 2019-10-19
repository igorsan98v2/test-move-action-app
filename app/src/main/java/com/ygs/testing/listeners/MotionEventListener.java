package com.ygs.testing.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Debug;
import android.util.Log;

public class MotionEventListener implements SensorEventListener {
    private float []values;

    @Override
    public void onSensorChanged(SensorEvent event) {
        values =  event.values;
        StringBuffer stringBuffer = new StringBuffer("");
        for(float val:values) {
            stringBuffer.append(val).append(" ");

        }
        Log.i("GRAVITY", "accel"+stringBuffer.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public float[] getValues() {
        return values;
    }
}
