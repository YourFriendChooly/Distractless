package com.project.distractless;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ToDoList extends AppCompatActivity
{
    public static ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list);
        /*
        Create Toolbar
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.todo_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Create List");

        ListView lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>
                (ToDoList.this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        /*
        Floating Action Button creates a dialogue to enter items to the to-do-list.
        Within the FAB On Click Listener is the Alert Dialog pop-up
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Alert Dialog builder will prompt the user to enter text for the to-do list entry
                and will proceed to allow the user to save or cancel the inputted text. That
                information, if saved, will call the writeItems method to save the data to a text file.
                 */
                AlertDialog.Builder builder = new AlertDialog.Builder(ToDoList.this);
                builder.setTitle("Create an Item");
                final EditText input = new EditText(ToDoList.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemsAdapter.add(input.getText().toString());
                        itemsAdapter.notifyDataSetChanged();
                        input.setText("");
                        writeItems();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.forward:
                runCheck();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void runCheck(){
        //TODO CODE THE INTENT IF/ELSE FOR IF ACTIVITY HAS BEEN RUN AND DIRECT FLOW.
        //Alarm Fragment.class will be changed to the advanced settings menu, once it has been created.
        startActivity(new Intent(ToDoList.this, alarmFragment.class));
    }

    public void readItems()
    {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems()
    {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


