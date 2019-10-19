package com.ygs.testing.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ygs.testing.R;
import com.ygs.testing.controller.DBAccess;
import com.ygs.testing.util.Status;

import java.util.Collection;

public class StatsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stats_view);
        Collection<Status> statusList = DBAccess.getInstace(this).loadStats();
        for(Status status:statusList){
            Log.i("STATUS",status.toString());
        }
        Log.i("STAT_DB","size"+statusList.size());

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        DBAccess.close();
    }
}
