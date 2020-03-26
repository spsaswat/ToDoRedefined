package com.fake_era.todoredefined;
//SqlHelper

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SqlHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="spDev";
    private static final int DB_VER = 1;
    public static final String DB_TABLE="Task";
    public static final String DB_COLUMN = "TaskName";
    public static final String DB_COLUMN_1 = "Time";
    public static final String DB_COLUMN_2 = "Status";

    public static final String DB_TABLE_ar="Archive";
    public static final String DB_COLUMN_ar = "Name";

    //constructor
    public SqlHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (%s TEXT NOT NULL,%s TEXT,%s TEXT NOT NULL,PRIMARY KEY (%s,%s));",DB_TABLE,DB_COLUMN,DB_COLUMN_1,DB_COLUMN_2,DB_COLUMN,DB_COLUMN_1);
        db.execSQL(query);
        String query1 = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY);",DB_TABLE_ar,DB_COLUMN_ar);
        db.execSQL(query1);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s",DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }


    public void insertNewTask(String task){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN,task);
        values.put(DB_COLUMN_1,formattedDate);
        values.put(DB_COLUMN_2,"0");
        db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteTask(String task,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DB_TABLE,new String[]{DB_COLUMN,DB_COLUMN_1,DB_COLUMN_2},DB_COLUMN+"=? AND "+DB_COLUMN_1+"=?",new String[]{task,date},null,null,DB_COLUMN_1);
        String task_det_ar="";
        while(cursor.moveToNext()){
            StringBuffer buffer=new StringBuffer();
            buffer.append(cursor.getString(0)+"\n");
            buffer.append(cursor.getString(1)+"\n");
            if(cursor.getString(2).equals("0"))
                buffer.append("PENDING");
            else
                buffer.append("COMPLETED");
            task_det_ar=buffer.toString();
        }
        cursor.close();

        //inserting
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN_ar,task_det_ar);
        db.insertWithOnConflict(DB_TABLE_ar,null,values,SQLiteDatabase.CONFLICT_REPLACE);


        db.delete(DB_TABLE,DB_COLUMN + "= ?"+" AND "+DB_COLUMN_1 + "= ?", new String[] {task,date});
        db.close();
    }



    public ArrayList<String> getTaskList(){
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE,new String[]{DB_COLUMN,DB_COLUMN_1,DB_COLUMN_2},null,null,null,null,DB_COLUMN_1);
        while(cursor.moveToNext()){
            StringBuffer buffer=new StringBuffer();
            buffer.append(cursor.getString(0)+"\n");
            buffer.append(cursor.getString(1)+"\n");
            if(cursor.getString(2).equals("0"))
            buffer.append("PENDING");
            else
                buffer.append("COMPLETED");
            taskList.add(buffer.toString());
        }
        cursor.close();
        db.close();
        return taskList;
    }




    //deletion in archive table
    public void deleteArchive(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ DB_TABLE_ar);
        db.close();
    }


    public void delete1Archive(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE_ar,DB_COLUMN_ar + "= ?", new String[] {task});
        db.close();
    }


    public ArrayList<String> getArchiveList(){
        ArrayList<String> ArchiveList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE_ar,new String[]{DB_COLUMN_ar},null,null,null,null,DB_COLUMN_ar);
        while(cursor.moveToNext()){
            StringBuffer buffer=new StringBuffer();
            buffer.append(cursor.getString(0));
            ArchiveList.add(buffer.toString());
        }
        cursor.close();
        db.close();
        return ArchiveList;
    }



















    //update in task table

    public void update_task_stat(String task, String date,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DB_COLUMN_2, status);
        db.update(DB_TABLE, cv, DB_COLUMN + "= ?"+" AND "+DB_COLUMN_1 + "= ?", new String[] {task,date});

    }


    public void update_task(String task, String date,String new_val) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DB_COLUMN, new_val);
        db.update(DB_TABLE, cv, DB_COLUMN + "= ?"+" AND "+DB_COLUMN_1 + "= ?", new String[] {task,date});

    }


    public void update_task_date(String task, String date,String new_val) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DB_COLUMN_1, new_val);
        db.update(DB_TABLE, cv, DB_COLUMN + "= ?"+" AND "+DB_COLUMN_1 + "= ?", new String[] {task,date});

    }
}
