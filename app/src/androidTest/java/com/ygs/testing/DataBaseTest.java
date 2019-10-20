package com.ygs.testing;

import android.content.Context;
import android.util.Log;

import com.ygs.testing.controller.DBAccess;
import com.ygs.testing.util.Energy;
import com.ygs.testing.util.Status;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DataBaseTest {
    @Test
    public void writeReadTestEnergy() throws NumberFormatException {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Energy energy = new Energy();

        energy.setStatus(0);
        DBAccess.getInstace(appContext).writeStat(energy);
        List stats =DBAccess.getInstace(appContext).loadStats();
        Status status = (Status) stats.get(stats.size()-1);
        Energy dbEnergy  = new Energy();
        dbEnergy.setStatus(status.getStatus());

        assertEquals(energy,dbEnergy);
    }


    @Test
    public void writeReadDate(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Date date  = new Date();
        Energy energy = new Energy();
        energy.setStatus(0);
        DBAccess.getInstace(appContext).writeStat(energy,date);
        List stats =DBAccess.getInstace(appContext).loadStats();
        Status status = (Status) stats.get(stats.size()-1);

        assertEquals(date.toString(),status.getDate().toString());
    }

}
