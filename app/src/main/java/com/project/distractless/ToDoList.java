package com.project.distractless;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list);

        //TODO Code to do list according to guide at https://guides.codepath.com/android/Basic-Todo-App-Tutorial

        ListView lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>
                (ToDoList.this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        Button devGo = (Button) findViewById(R.id.devGo);

        devGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToDoList.this, alarmFragment.class));
            }
        });
    }

    public void onAddItem(View v)
    {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        itemsAdapter.notifyDataSetChanged();
        etNewItem.setText("");
        writeItems(); // <---- Add this line
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

    public int getItems()
    { int number = items.size();
    return number;
    }

}
