package com.ygs.testing.listeners;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.ygs.testing.MainActivity;
import com.ygs.testing.R;
import com.ygs.testing.activity.StatsActivity;
import com.ygs.testing.controller.DBAccess;
import com.ygs.testing.util.Status;

import java.util.Collection;
import java.util.List;

public class ButtonListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                Log.d("BUTTON","clicked");


                Intent intent = new Intent(v.getContext(), StatsActivity.class);
                v.getContext().startActivity(intent);
                break;
        }
    }
}