package com.ygs.testing;

import android.content.Context;

import com.google.gson.Gson;
import com.ygs.testing.services.NetworkService;
import com.ygs.testing.util.Energy;

import org.junit.Test;


import okhttp3.ResponseBody;
import retrofit2.Response;

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

                Response response  =NetworkService.getInstance().getJSONApi().sendData(energy).execute();
                Energy respond = new Gson().fromJson(response.message(),Energy.class);
                assertEquals(energy,respond);



        }
        catch(Exception e){
            e.printStackTrace();

        }

    }

    @Test
    public void writeWrongData() {
        Energy energy = new Energy();
        try {

            energy.setStatus(1);
            energy.setStatus(0);
            energy.setStatus(12);
            energy.setStatus(null);
            energy.setStatus(-12);
            assertTrue(true);
        }
        catch (NumberFormatException e){
            assertTrue(true);
            e.printStackTrace();
            return;

        }
        catch (NullPointerException e){
            assertTrue(true);
            e.printStackTrace();
            return;
        }
        assertTrue(false);






    }

}