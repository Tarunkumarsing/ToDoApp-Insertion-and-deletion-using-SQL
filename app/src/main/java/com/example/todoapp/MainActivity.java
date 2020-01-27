package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Dbhelper dbhelper;
    ArrayAdapter<String > mAdapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        dbhelper = new Dbhelper(this);

        loadTask();

    }
        //Addd icon to menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //LOAD ALL TASK
    private void loadTask(){
        ArrayList<String> tasklist = dbhelper.getTaskList();


        if (mAdapter == null){
            mAdapter = new ArrayAdapter<String>(this,R.id.task_title, tasklist);
            listView.setAdapter(mAdapter);
        }else{
            mAdapter.clear();
            mAdapter.addAll(tasklist);
            mAdapter.notifyDataSetChanged();
        }

    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.addtask:
                final EditText editText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add new Task")
                        .setMessage("Whats your task ")
                        .setView(editText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(editText.getText());
                                dbhelper.insertNewTask(task);

                                loadTask();
                            }
                        })
                        .setNegativeButton("CANCEL", null)
                        .create();
                dialog.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deletetask(View view){
        try {
            int index = listView.getPositionForView(view);
            String task = mAdapter.getItem(index++);
            dbhelper.deletetask(task);
            loadTask();
        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage().toString(), Toast.LENGTH_SHORT).show();

        }
    }
}

