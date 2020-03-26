package com.fake_era.todoredefined;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class update_ extends AppCompatActivity {
String task,date,status,t;
TextView tv;
EditText et1,et2;
SqlHelper MyDb;
MainActivity mt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_);
        MyDb = new SqlHelper(this);
        mt=new MainActivity();
        Intent mIntent = getIntent();
         task = mIntent.getStringExtra("VariableName1");
        date = mIntent.getStringExtra("VariableName2");
        status = mIntent.getStringExtra("VariableName3");
        tv=(TextView) findViewById(R.id.textView3);
        et2=(EditText) findViewById(R.id.editText2);
        et1=(EditText) findViewById(R.id.editText);
        show_stat();
        et2.setText(date);
        et1.setText(task);
    }

    public void show_stat() {


        //0=pending 1=completed

        if(status.equals("COMPLETED"))
        {
           tv.setText("COMPLETED");

           tv.setBackgroundColor(ContextCompat.getColor(this,R.color.C13_Green));
            tv.setTextColor(ContextCompat.getColor(this,R.color.C13_Yellow));

        }
        else
        {
            tv.setText("PENDING");
            tv.setBackgroundColor(ContextCompat.getColor(this,R.color.C6_Navy));
            tv.setTextColor(ContextCompat.getColor(this,R.color.C6_Yellow));
        }

        //MyDb.update_task_stat(task,t);


        }

    public void change_status(View view) {
        if(status.equals("COMPLETED"))
        {
            tv.setText("PENDING");
            tv.setTextColor(ContextCompat.getColor(this,R.color.C6_Yellow));
            tv.setBackgroundColor(ContextCompat.getColor(this,R.color.C6_Navy));
            status="PENDING";

        }
        else
        {
            tv.setText("COMPLETED");

            tv.setBackgroundColor(ContextCompat.getColor(this,R.color.C13_Green));
            tv.setTextColor(ContextCompat.getColor(this,R.color.C13_Yellow));
            status="COMPLETED";
        }
        if(status.equals("PENDING")){
            t="0";
        }
        else t="1";

        MyDb.update_task_stat(task,date,t);

    }

    public void update_date(View view) {
        String new_val;
        et2=(EditText) findViewById(R.id.editText2);
        new_val=et2.getText().toString();
        MyDb.update_task_date(task,date,new_val);
        date=new_val;
    }

    public void update_task(View view) {
        String new_val;
        et1=(EditText) findViewById(R.id.editText);
        new_val=et1.getText().toString();
        MyDb.update_task(task,date,new_val);
        task=new_val;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }


}
