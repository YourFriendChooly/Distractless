package com.project.distractless;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PinTutorial extends AppCompatActivity
{
    TextView txtPinTutorial;
    Button butPinTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_tutorial);

        txtPinTutorial = (TextView) findViewById(R.id.txtPinTutorial);
        butPinTutorial = (Button) findViewById(R.id.butPinTutorial);

        butPinTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PinTutorial.this, Pin.class));
            }
        });
    }
}
