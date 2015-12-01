package com.project.distractless;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TimerTutorial extends AppCompatActivity
{

    TextView txtTimerTutorial;
    Button butTimerTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_tutorial);

        txtTimerTutorial = (TextView) findViewById(R.id.txtTimerTutorial);
        butTimerTutorial = (Button) findViewById(R.id.butTimerTutorial);

        butTimerTutorial.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(TimerTutorial.this, setAlarm.class));
            }
        });
    }
}