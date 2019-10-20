package com.ygs.testing;

import com.ygs.testing.services.NetworkService;
import com.ygs.testing.util.Energy;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public  void retrofitTest(){
        Energy energy = new Energy();
        energy.setStatus(1);
        try {

             Energy respond= NetworkService.getInstance().sendEnergy(energy);

             assertEquals(energy,respond);
        }
        catch (IllegalMonitorStateException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

    }

}