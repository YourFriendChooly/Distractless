package com.project.distractless;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
        ListView lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>
                (ToDoList.this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        /*
        Floating Action Button creates a dialogue to enter items to the to-do-list
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Code Dialog pop-up for text entry.
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
        writeItems();
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
