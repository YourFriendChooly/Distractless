package com.project.distractless;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalTutorial extends AppCompatActivity
{

    TextView txtFinalTutorial;
    Button butFinalTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_tutorial);

        txtFinalTutorial = (TextView) findViewById(R.id.txtToDoTutorial);
        butFinalTutorial = (Button) findViewById(R.id.butToDoTutorial);

        butFinalTutorial.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO Change shared preferences to not run tutorial
                startActivity(new Intent(FinalTutorial.this, MainActivity.class));
            }
        });
    }
}
