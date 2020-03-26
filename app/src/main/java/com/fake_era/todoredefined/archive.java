package com.fake_era.todoredefined;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class archive extends AppCompatActivity {
    SqlHelper MyDb;
    TextView tv;
    ArrayAdapter<String> mAdapter;
    //ArrayAdapter<String> dAdapter;
    ListView ATask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        MyDb = new SqlHelper(this);


        ATask = (ListView)findViewById(R.id.ATask);


       loadArchiveList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ar,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_del:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Warning!")
                        .setMessage("Are You Sure You Want to delete all")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyDb.deleteArchive();
                                loadArchiveList();
                            }
                        })
                        .setNegativeButton("No",null)
                        .create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadArchiveList() {
        ArrayList<String> ArchiveList = MyDb.getArchiveList();
        if(mAdapter==null){
            mAdapter = new ArrayAdapter<String>(this,R.layout.gen_row_1,R.id.task_archive,ArchiveList);
            ATask.setAdapter(mAdapter);

        }
        else{
            mAdapter.clear();
            mAdapter.addAll(ArchiveList);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void del1arc(View view) {
        View parent = (View)view.getParent();
        TextView taskTextView = (TextView)parent.findViewById(R.id.task_archive);
        String str=taskTextView.getText().toString();
        MyDb.delete1Archive(str);
        loadArchiveList();
    }
}
