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

public class MainActivity extends AppCompatActivity {

    SqlHelper MyDb;
    TextView tv;
    ArrayAdapter<String> mAdapter;
    //ArrayAdapter<String> dAdapter;
    ListView lstTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyDb = new SqlHelper(this);


        lstTask = (ListView)findViewById(R.id.lstTask);


        loadTaskList();
    }




    private void loadTaskList() {
        ArrayList<String> taskList = MyDb.getTaskList();
        if(mAdapter==null){
            mAdapter = new ArrayAdapter<String>(this,R.layout.gen_row,R.id.task_title,taskList);
            lstTask.setAdapter(mAdapter);

        }
        else{
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        //Change menu icon color
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Create a New Task")
                        .setMessage("Example:- Play Football")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                MyDb.insertNewTask(task);
                                loadTaskList();
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .create();
                dialog.show();
                return true;
            case R.id.action_archive:
                Intent myIntent = new Intent(this, archive.class);
                startActivity(myIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteTask(View view){
        View parent = (View)view.getParent();
        TextView taskTextView = (TextView)parent.findViewById(R.id.task_title);
        String date,task,str=taskTextView.getText().toString();
        int index=1+str.indexOf("\n");
        task=str.substring(0,index-1);
        date=str.substring(index,index+10);
        MyDb.deleteTask(task,date);
        loadTaskList();
    }


public void update_(View view)
{
    View parent = (View)view.getParent();
    TextView taskTextView = (TextView)parent.findViewById(R.id.task_title);
    String status,task,date,str=taskTextView.getText().toString();
    int index=1+str.indexOf("\n");
    task=str.substring(0,index-1);
    date=str.substring(index,index+10);
    status=str.substring(index+11);
    Intent myIntent = new Intent(this, update_.class);
    myIntent.putExtra("VariableName1",task );
    myIntent.putExtra("VariableName2", date);
    myIntent.putExtra("VariableName3", status);
    startActivity(myIntent);
}



//public String get_l_(String word)
//{
//    if (word.length() > 12) {
//        return word.substring(word.length() - 12,word.length()-2);
//    } else {
//        return "Date not found";
//    }
//}
@Override
public void onBackPressed() {
    Intent a = new Intent(Intent.ACTION_MAIN);
    a.addCategory(Intent.CATEGORY_HOME);
    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(a);
}


}
