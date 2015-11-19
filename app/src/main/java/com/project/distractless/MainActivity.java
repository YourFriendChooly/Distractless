package com.project.distractless;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    //String for password
    public String key = "a";

    TextView keyPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView keyPrompt = (TextView) findViewById(R.id.keyPrompt);
        //TODO Either code a Submit button or create a listener for when Key == Stored Key ...  then figure out how listeners work
        //TODO Variate keyPrompt for if a key already exists in memory
        //TODO Code memory... somehow
    }

        //Code for NumberPad
    public void numPad (View view){
        switch(view.getId()){
            case R.id.numPad1:
                numData("1");
                break;
            case R.id.numPad2:
                numData("2");
                break;
            case R.id.numPad3:
                numData("3");
                break;
            case R.id.numPad4:
                numData("4");
                break;
            case R.id.numPad5:
                numData("5");
                break;
            case R.id.numPad6:
                numData("6");
                break;
            case R.id.numPad7:
                numData("7");
                break;
            case R.id.numPad8:
                numData("8");
                break;
            case R.id.numPad9:
                numData("9");
                break;
            case R.id.numPad0:
                numData("0");
                break;
        }
    }

    //Adds values to key
    public void numData (String num){
        key = key+num;
        if (key.length() >= 7){
            keyPrompt = (TextView) findViewById(R.id.keyPrompt);
            keyPrompt.setText("Great!");
        }
    }
}
