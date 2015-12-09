package com.project.distractless;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
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


public class ToDoList extends ActionBarActivity {
    public static ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    public static boolean runNow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list);
        /*
        Run Check to see if there is an existing to-do-list stored locally on the phone. If it
        is present, prompt the user to either load the old to-do list or create a new one.
         */
        ListView lvItems = (ListView) findViewById(R.id.lvItems);
        AlertDialog.Builder prompt = new AlertDialog.Builder(ToDoList.this);
        final AlertDialog.Builder warning = new AlertDialog.Builder(ToDoList.this);

        readItems();
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>
                (ToDoList.this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        if (readItems()) {
            prompt.setTitle("Previous To-Do List Found");
            prompt.setMessage("Would you like to load your old list, or start a new one?");

            prompt.setPositiveButton("NEW LIST", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    items.clear();
                }
            });
            prompt.setNegativeButton("LOAD LIST", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    readItems();
                    itemsAdapter.addAll(items);
                    itemsAdapter.notifyDataSetChanged();
                }
            });
            AlertDialog dialogAlert = prompt.create();
            dialogAlert.show();
        }



        /*
        Create Toolbar
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.todo_toolbar);
        toolbar.setTitle("Create A List!");
        toolbar.inflateMenu(R.menu.todo_toolbar);
        toolbar.findViewById(R.id.forward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeItems();
                if (items.size() > 0) {
                    if (!runNow) {
                        Intent intent = Tutorial.ActivitySwitch(ToDoList.this, Splash.class, 3);
                        startActivity(intent);
                    } else {
                        Intent intent = Tutorial.ActivitySwitch(ToDoList.this, AlarmFragment.class, 4);
                        startActivity(intent);
                    }
                }else {
                    warning.setTitle("Uh-Oh!");
                    warning.setMessage("Your List Is Empty!");
                    warning.setPositiveButton("Whoops!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialogWarning = warning.create();
                    dialogWarning.show();
                }


            }
        });




        /*
        Floating Action Button creates a dialogue to enter items to the to-do-list.
        Within the FAB On Click Listener is the Alert Dialog pop-up
         */
        FloatingActionButton todoFab = (FloatingActionButton) findViewById(R.id.todoFab);
        todoFab.setOnClickListener(new View.OnClickListener() {
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
                input.setHint("Enter an Item");
                input.setTextColor(getResources().getColor(R.color.black_overlay));
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String itemText = input.getText().toString();
                        itemsAdapter.add(itemText);
                        itemsAdapter.notifyDataSetChanged();
                        items.add(itemText);
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
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.forward:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));
            if (items.size() > 0)
                return true;
            else
                items = new ArrayList<>();
            return false;
        } catch (IOException e) {
            items = new ArrayList<>();
            return false;
        }

    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
            items.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


