package com.ygs.testing.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * using for openup db that use
 * {@link DBAccess} class
 * */
public class DBHelper  extends SQLiteOpenHelper {
        final  String LOG_TAG = "DATABASE";
        private Context context;
        DBHelper(Context context) {
            super(context, "statusDB", null, 1);
            this.context =context;
        }
        /**
         *
         * init table if table still does not exist
         * @param db is database that decelerated in superclass constructor
         * */
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            //create table
            db.execSQL("create table if not exists stats ("
                    + "id integer primary key autoincrement,"
                    + "time integer,"
                    + "status integer" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }



    Context getContext() {
        return context;
    }
}


