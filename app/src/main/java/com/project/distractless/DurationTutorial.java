package com.project.distractless;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DurationTutorial extends AppCompatActivity {

    TextView txtToDoTutorial;
    Button butDurationTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_tutorial);

        txtToDoTutorial = (TextView) findViewById(R.id.txtToDoTutorial);
        butDurationTutorial = (Button) findViewById(R.id.butToDoTutorial);

        butDurationTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startActivity(new Intent(DurationTutorial.this, Duration.class));
            }
        });
    }
}
