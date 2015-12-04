package com.project.distractless;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Tutorial extends AppCompatActivity {

    public static boolean setRunAssistant = false;

    TextView txtTutorial;
    Button butTutorial;
    public static int setTutorialState;
    
    public static Intent ActivitySwitch(Context context, Class mainClass, int passTutorialState) {
        if (!setRunAssistant) {
            Intent intent = new Intent(context, mainClass);
            return intent;
        } else {
            Intent intent = new Intent(context, Tutorial.class);
            Tutorial.setTutorialState = passTutorialState;
            return intent;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        txtTutorial = (TextView) findViewById(R.id.txtTutorial);
        butTutorial = (Button) findViewById(R.id.butTutorial);
        TutorialCheck tc = new TutorialCheck();

        //Pin Tutorial
        if (setTutorialState == 0){
            txtTutorial.setText(R.string.txtPinTutorial);
            butTutorial.setText(R.string.butTutorial);
            butTutorial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Tutorial.this, Pin.class));
                }
            });

        //Alarm Tutorial
        }else if (setTutorialState == 1){
            txtTutorial.setText(R.string.txtTimerTutorial);
            butTutorial.setText(R.string.butTutorial);
            butTutorial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Tutorial.this, SetAlarm.class));
                }
            });

        //To Do List Tutorial
        }else if (setTutorialState == 2) {
            txtTutorial.setText(R.string.txtToDoTutorial);
            butTutorial.setText(R.string.butTutorial);
            butTutorial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Tutorial.this, ToDoList.class));
                }
            });

        //Final Tutorial
        }else if (setTutorialState == 3){
            txtTutorial.setText(R.string.txtFinalTutorial);
            butTutorial.setText(R.string.butFinalTutorial);
            butTutorial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Tutorial.this, Splash.class));
                }
            });
        }

    }
}
