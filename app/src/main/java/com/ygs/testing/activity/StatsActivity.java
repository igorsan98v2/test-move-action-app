package com.ygs.testing.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ygs.testing.R;
import com.ygs.testing.controller.DBAccess;
import com.ygs.testing.util.RecycleAdapter;
import com.ygs.testing.util.Status;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StatsActivity extends Activity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.statistic_layout);
        List<Status> statusList = DBAccess.getInstace(this).loadStats();
        for(Status status:statusList){
            Log.i("STATUS",status.toString());
        }
        Log.i("STAT_DB","size"+statusList.size());
        recyclerView = (RecyclerView) findViewById(R.id.statsRecycleView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        RecycleAdapter recycleAdapter = new RecycleAdapter(statusList);
        recyclerView.setAdapter(recycleAdapter);


    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        DBAccess.close();
    }
}
