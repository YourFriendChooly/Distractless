package com.project.distractless;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ToDoListTutorial extends AppCompatActivity {

    TextView txtToDoTutorial;
    Button butToDoTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_tutorial);

        txtToDoTutorial = (TextView) findViewById(R.id.txtToDoTutorial);
        butToDoTutorial = (Button) findViewById(R.id.butToDoTutorial);

        butToDoTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToDoListTutorial.this, ToDoList.class));
            }

        });
    }
}
