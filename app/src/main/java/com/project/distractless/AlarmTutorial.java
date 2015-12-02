package com.project.distractless;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AlarmTutorial extends AppCompatActivity
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
                startActivity(new Intent(AlarmTutorial.this, SetAlarmtemp.class));
            }
        });
    }
}