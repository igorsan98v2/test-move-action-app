package com.ygs.testing.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.ygs.testing.util.Energy;
import com.ygs.testing.util.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ihor Ytsyk
 *
 * used for accesing to db
 * read and write data based on
 * {@link com.ygs.testing.util.Status}
 *
 * */

public class DBAccess {
    private DBHelper dbHelper;
   private static DBAccess dbAccess  = new DBAccess();
   private DBAccess(){

   }
   public static DBAccess getInstace(Context context){
       try {


           if (dbAccess.dbHelper == null || (dbAccess.dbHelper!=null && !context.equals(dbAccess.dbHelper.getContext()))) {
               dbAccess.dbHelper = new DBHelper(context);
           }
       }
       catch (NullPointerException e){
           Log.e("ERROR",e.getMessage());
       }
       return dbAccess;
   }

   public  void writeStat(Energy energy){
       writeStat(energy,new Date());
   }
   public void writeStat(Energy energy,Date date){
       ContentValues cv = new ContentValues();
       SQLiteDatabase db = dbHelper.getWritableDatabase();
       cv.put("status",energy.getStatus());
       cv.put("time", (int) (date.getTime()/1000));//convert date to int
       long rowID = db.insert("stats", null, cv);
       Log.d("INSERT", "row inserted, ID = " + rowID);
   }
   public List<Status> loadStats(){
       List<Status> stats = new ArrayList<Status>();
       SQLiteDatabase db = dbHelper.getWritableDatabase();
       Cursor c = db.query("stats", null, null, null, null, null, null);


       if (c.moveToFirst()) {


           int idIndex = c.getColumnIndex("id");
           int statusIndex = c.getColumnIndex("status");
           int timeIndex = c.getColumnIndex("time");

           do {
               int id = c.getInt(idIndex);
               int status=  c.getInt(statusIndex);
               int time = c.getInt(timeIndex);
               stats.add(new Status(id,status,new Date(((long)time)*1000L)));

           } while (c.moveToNext());
       }
       c.close();

       return stats;
   }
    public  static void close(){
       dbAccess.dbHelper.close();
    }
}